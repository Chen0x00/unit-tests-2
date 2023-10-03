package com.rick.financial_dataService.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rick.financial_api.common.constant.LoanConstant;
import com.rick.financial_api.common.util.CommonUtil;
import com.rick.financial_api.domain.FinanceAccount;
import com.rick.financial_api.domain.RechargeRecord;
import com.rick.financial_api.service.RechargeRecordService;
import com.rick.financial_dataService.mapper.FinanceAccountMapper;
import com.rick.financial_dataService.mapper.RechargeRecordMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ASUS
 * @description 针对表【b_recharge_record(充值记录表)】的数据库操作Service实现
 * @createDate 2023-09-19 16:40:47
 */
@DubboService(interfaceClass = RechargeRecordService.class, version = "1.0")
@Service
@Transactional
public class RechargeRecordServiceImpl extends ServiceImpl<RechargeRecordMapper, RechargeRecord>
        implements RechargeRecordService {

    @Resource
    private RechargeRecordMapper rechargeRecordMapper;

    @Resource
    private FinanceAccountMapper financeAccountMapper;

    //添加充值记录
    @Override
    public int addRechargeRecord(RechargeRecord record) {
        return rechargeRecordMapper.insert(record);
    }

    /**
     * 查充值记录数
     */
    @Override
    public long queryRecordByUid(Integer uid) {
        long count = 0;
        if (uid != null && uid > 0) {
            QueryWrapper<RechargeRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uid", uid);
            count = rechargeRecordMapper.selectCount(queryWrapper);
        }
        return count;
    }

    /**
     *查充值信息
     */

    @Override
    public List<RechargeRecord> queryByUid(Integer uid, Integer pageNo, Integer pageSize) {
        ArrayList<RechargeRecord> rechargeRecords = new ArrayList<>();

        if (uid != null && uid > 0) {
            pageNo = CommonUtil.PageNo(pageNo);
            pageSize = CommonUtil.PageSize(pageSize);
            Integer offset = (pageNo - 1) * pageSize;
            rechargeRecords = rechargeRecordMapper.getAllByUid(uid, offset, pageSize);
        }
        return rechargeRecords;
    }


    /**
     *处理快钱充值
     */
    @Override
    public synchronized int handleKQNotify(String orderId, String payAmount, String payResult) {
        int result = 0;//订单不存在
        int rows =  0;
        //1.查询订单
        RechargeRecord record = rechargeRecordMapper.selectById(orderId);
        if(record != null ){
            if( record.getRechargeStatus() == LoanConstant.RECHARGE_STATUS_PROCESSING){
                //2.判断金额是否一致
                String fen = record.getRechargeMoney().multiply(new BigDecimal("100"))
                        .stripTrailingZeros().toPlainString();
                if( fen.equals(payAmount)){
                    //金额一致
                    if("10".equals(payResult)){
                        //成功
                        rows = financeAccountMapper.updateAvailableMoneyByRecharge(record.getUid(),record.getRechargeMoney());
                        if(rows < 1 ){
                            throw new RuntimeException("充值更新资金账号失败");
                        }
                        //更新充值记录的状态
                        rows = rechargeRecordMapper.updateStatus(record.getId(),LoanConstant.RECHARGE_STATUS_SUCCESS);
                        if( rows < 1) {
                            throw new RuntimeException("充值更新充值记录状态失败");
                        }
                        result  = 1;//成功
                    } else {
                        //充值失败
                        //更新充值记录的状态
                        rows = rechargeRecordMapper.updateStatus(record.getId(),LoanConstant.RECHARGE_STATUS_FAIL);
                        if( rows < 1) {
                            throw new RuntimeException("充值更新充值记录状态失败");
                        }
                        result = 2;//充值结果是失败的
                    }
                } else {
                    result = 4;//金额不一样
                }
            } else {
                result = 3;//订单已经处理过了
            }
        }
        return result;
    }
}




