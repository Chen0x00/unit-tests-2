package com.rick.financial_api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rick.financial_api.domain.RechargeRecord;

import java.util.List;


/**
* @author ASUS
* @description 针对表【b_recharge_record(充值记录表)】的数据库操作Service
* @createDate 2023-09-19 16:40:47
*/
public interface RechargeRecordService extends IService<RechargeRecord> {

    /**
     * 查询用户充值记录
     */
    List<RechargeRecord> queryByUid(Integer uid,Integer pageNo,Integer pageSize);

    //充值记录数
    long queryRecordByUid(Integer uid);

    //添加充值纪录
    int addRechargeRecord(RechargeRecord record);

    //处理充值的信息
    int handleKQNotify(String orderId, String payAmount, String payResult);
}
