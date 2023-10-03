package com.rick.financial_dataService.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rick.financial_api.domain.RechargeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;


/**
* @author ASUS
* @description 针对表【b_recharge_record(充值记录表)】的数据库操作Mapper
* @createDate 2023-09-19 16:40:47
* @Entity database.domain.RechargeRecord
*/
public interface RechargeRecordMapper extends BaseMapper<RechargeRecord> {

    /**
     * 查询充值信息
     * @param offset 偏移量
     */
    ArrayList<RechargeRecord> getAllByUid(@Param("uid") Integer uid,@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);


    /*更新充值的状态*/
    int updateStatus(@Param("id") Integer id, @Param("newStatus") int rechargeStatusSuccess);



}




