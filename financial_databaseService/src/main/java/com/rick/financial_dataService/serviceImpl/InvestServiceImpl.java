package com.rick.financial_dataService.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rick.financial_api.common.constant.LoanConstant;
import com.rick.financial_api.common.util.CommonUtil;
import com.rick.financial_api.domain.BidInfo;
import com.rick.financial_api.domain.FinanceAccount;
import com.rick.financial_api.domain.LoanInfo;
import com.rick.financial_api.pojo.BidAndLoan;
import com.rick.financial_api.service.InvestInfoService;
import com.rick.financial_dataService.mapper.BidInfoMapper;
import com.rick.financial_dataService.mapper.FinanceAccountMapper;
import com.rick.financial_dataService.mapper.LoanInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DubboService(interfaceClass = InvestInfoService.class, version = "1.0")
@Service
@Transactional
public class InvestServiceImpl implements InvestInfoService {

    @Resource
    private BidInfoMapper bidInfoMapper;


    @Resource
    private FinanceAccountMapper financeAccountMapper;

    @Resource
    private LoanInfoMapper loanInfoMapper;

    /**
     * 某个产品的投资记录
     */
    @Override
    public List<BidAndLoan> getBLByLoanId(Integer loanId, Integer pageNo, Integer pageSize) {
        List<BidAndLoan> blByLoanId = new ArrayList<>();
        if (loanId != null && loanId > 0) {
            pageNo = CommonUtil.PageNo(pageNo);
            pageSize = CommonUtil.PageSize(pageSize);
            Integer offset = (pageNo - 1) * pageSize;
            blByLoanId = bidInfoMapper.getBLByLoanId(loanId, offset, pageSize);
        }

        return blByLoanId;
    }


    /**
     * 购买理财产品,0为参数不正确，1为成功，2为账户不存在，3为用户可用余额不足，4为产品不存在，5为投资金额超过投标要求
     */
    @Override
    public  int investProduct(Integer uid, Integer loanId, BigDecimal money) {//加上同步锁，可以防止用户同时多线程消费，但是性能会降低，所以用乐观锁
        int result = 0;//参数不正确
        //检查参数
        if ((uid != null && uid > 0) && (loanId != null && loanId > 0) && (money != null && money.doubleValue() >= 100.00)) {
            //查询账户
            FinanceAccount account = financeAccountMapper.queryByUidForUpdate(uid);
            if (account != null) {
                //查询余额是否比要消费的金额大
                if (CommonUtil.compareTo(account.getAvailableMoney(), money)) {
                    //检查产品是否可以购买
                    LoanInfo loanInfo = loanInfoMapper.selectById(loanId);
                    if (loanInfo != null && loanInfo.getProductStatus() == LoanConstant.PRODUCT_STATUS_NOT_SELLOUT) {//产品存在且可以消费
                        //判断消费金额是否在投标要求的上下限内
                        if (CommonUtil.compareTo(loanInfo.getLeftProductMoney(), money) &&//剩余投标金额是否比要消费金额大
                                CommonUtil.compareTo(money, loanInfo.getBidMinLimit()) &&//最小消费
                                CommonUtil.compareTo(loanInfo.getBidMaxLimit(), money)) {//最大消费
                            //开始消费，使用乐观锁更新账户可用金额
                             int updateAccountRows = financeAccountMapper.updateAvailableMoneyWithVersion(uid, money,account.getVersion());
                            if (updateAccountRows >= 1) {
                                //扣除产品剩余投标金额
                                int updateProductRows = loanInfoMapper.updateLeftProductMoney(loanInfo.getId(), money);
                                if (updateProductRows >= 1) {//消费成功
                                    //创建投资记录
                                    Date current = new Date();
                                    BidInfo bidInfo = new BidInfo(loanId,uid,money,current,LoanConstant.BID_STATUS_SUCCESS);
                                    int bidInsertRows = bidInfoMapper.insert(bidInfo);
                                    if (bidInsertRows < 1) {
                                        System.out.println("投资中创建投资记录失败");
                                    }
                                    //如果卖完了，更新产品信息
                                    LoanInfo newLoanInfo = loanInfoMapper.selectById(loanId);
                                    if (newLoanInfo.getLeftProductMoney().compareTo(new BigDecimal("0")) == 0) {
                                        UpdateWrapper<LoanInfo> updateWrapper = new UpdateWrapper<>();
                                        updateWrapper.set("product_status", LoanConstant.PRODUCT_STATUS_SELLOUT)
                                                .eq("id", loanId);
                                        int update = loanInfoMapper.update(newLoanInfo, updateWrapper);
                                        if (update < 1) {
                                            throw new RuntimeException("投资中更新产品状态为满标失败");
                                        }
                                    }
                                    //投资成功
                                    result = 1;

                                } else {//非检查异常（Unchecked Exception）
                                    throw new RuntimeException("投资中更新产品剩余资金失败");
                                }
                            } else {
                                throw new RuntimeException("投资更新用户账户可用资金失败");
                            }
                        } else {//投资金额超过投标要求
                            result = 5;
                        }

                    } else {//产品不存在
                        result = 4;
                    }
                } else {//余额不足
                    result = 3;
                }

            } else {//账户不存在
                result = 2;
            }
        }

        return result;

    }





}
