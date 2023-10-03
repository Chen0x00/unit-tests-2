package com.rick.financial_dataService.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rick.financial_api.domain.IncomeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


/**
* @author ASUS
* @description 针对表【b_income_record(收益记录表)】的数据库操作Mapper
* @createDate 2023-09-19 16:40:47
* @Entity database.domain.IncomeRecord
*/
public interface IncomeRecordMapper extends BaseMapper<IncomeRecord> {
    //到期的收益记录
    List<IncomeRecord> selectExpiredIncome(@Param("expiredDate") Date expiredDate);




}




