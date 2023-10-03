package com.rick.financial_dataService.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rick.financial_api.common.constant.LoanConstant;
import com.rick.financial_api.domain.BidInfo;
import com.rick.financial_api.domain.IncomeRecord;
import com.rick.financial_api.domain.LoanInfo;
import com.rick.financial_api.pojo.BidAndLoan;
import com.rick.financial_api.service.IncomeRecordService;
import com.rick.financial_dataService.mapper.BidInfoMapper;
import com.rick.financial_dataService.mapper.FinanceAccountMapper;
import com.rick.financial_dataService.mapper.IncomeRecordMapper;
import com.rick.financial_dataService.mapper.LoanInfoMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
* @author ASUS
* @description 针对表【b_income_record(收益记录表)】的数据库操作Service实现
* @createDate 2023-09-19 16:40:47
*/
@DubboService(interfaceClass = IncomeRecordService.class,version = "1.0")
@Service
@Transactional
public class IncomeRecordServiceImpl extends ServiceImpl<IncomeRecordMapper, IncomeRecord>
    implements IncomeRecordService {

    @Resource
    private LoanInfoMapper loanInfoMapper;
    @Resource
    private BidInfoMapper bidInfoMapper;
    @Resource
    private IncomeRecordMapper incomeRecordMapper;
    @Resource
    private FinanceAccountMapper financeAccountMapper;

    //收益计划
    @Override
    public void generateIncomePlan() {
        //获取要处理的理财产品记录
        Date current = new Date();
        Date begin = DateUtils.truncate(DateUtils.addDays(current, -1), Calendar.DATE);
        Date end = DateUtils.truncate(current, Calendar.DATE);
        List<LoanInfo> loanInfos = loanInfoMapper.queryFullTimeLoan(begin, end);

        //查询每个借款的投资记录
        int rows = 0;

        BigDecimal income = null;
        BigDecimal dayRate = null;
        BigDecimal cycle = null;//周期

        Date incomeDate = null;//到期时间

        for (LoanInfo loanInfo : loanInfos) {
            //计算日利率
            dayRate = loanInfo.getRate().divide(new BigDecimal("360"), 10, BigDecimal.ROUND_HALF_UP)
                    .divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
            //根据产品类型划分
            if (loanInfo.getProductType() == LoanConstant.PRODUCT_TYPE_ROOKIE) {
                cycle = new BigDecimal(loanInfo.getCycle());//日
                incomeDate = DateUtils.addDays(loanInfo.getProductFullTime(), (1 + loanInfo.getCycle()));
            } else {
                cycle = new BigDecimal(loanInfo.getCycle()*30);//月
                incomeDate = DateUtils.addDays(loanInfo.getProductFullTime(), (1 + loanInfo.getCycle()*30));
            }
            List<BidInfo> bidInfoList = bidInfoMapper.getByLoanId(loanInfo.getId());
            //计算利息和到期时间
            for (BidInfo bidInfo : bidInfoList) {
                //利息=本金*周期*利率
                income = bidInfo.getBidMoney().multiply(cycle).multiply(dayRate);
                //创建收益纪录
                IncomeRecord incomeRecord = new IncomeRecord();
                incomeRecord.setBidId(bidInfo.getId());
                incomeRecord.setBidMoney(bidInfo.getBidMoney());
                incomeRecord.setIncomeDate(incomeDate);
                incomeRecord.setIncomeStatus(LoanConstant.INCOME_STATUS_PLAN);
                incomeRecord.setLoanId(loanInfo.getId());
                incomeRecord.setIncomeMoney(income);
                incomeRecord.setUid(bidInfo.getUid());
                incomeRecordMapper.insert(incomeRecord);
            }

            //更新产品状态
            rows=loanInfoMapper.updateStatus(loanInfo.getId(),LoanConstant.PRODUCT_STATUS_PLAN);
            if (rows < 1) {
                throw new RuntimeException("生成收益计划，但是更新状态2失败");
            }

        }



    }

    /**
     * 返回利息
     */
    @Override
    public void generateIncomeBack() {

        //1.获取要处理的到期的收益记录
        Date curDate = new Date();
        Date expiredDate = DateUtils.truncate(DateUtils.addDays(curDate, -1),Calendar.DATE);
        System.out.println("expiredDate="+expiredDate);
        List<IncomeRecord> incomeRecordList = incomeRecordMapper.selectExpiredIncome(expiredDate);

        int rows  = 0;
        //2.把每个收益，进行返还， 本金 + 利息
        for(IncomeRecord ir : incomeRecordList){
            rows  = financeAccountMapper.updateAvailableMoneyByIncomeBack(ir.getUid(),ir.getBidMoney(),ir.getIncomeMoney());
            if( rows < 1 ){
                throw new RuntimeException("收益返还，更新账号资金失败");
            }

            //3.更新收益记录的状态为 1
            ir.setIncomeStatus(LoanConstant.INCOME_STATUS_BACK);
            rows = incomeRecordMapper.updateById(ir);
            if( rows <1) {
                throw new RuntimeException("收益返还，更新收益记录的状态失败");
            }
        }

    }




}




