package com.rick.financial_api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rick.financial_api.domain.IncomeRecord;


/**
* @author ASUS
* @description 针对表【b_income_record(收益记录表)】的数据库操作Service
* @createDate 2023-09-19 16:40:47
*/
public interface IncomeRecordService extends IService<IncomeRecord> {

    //收益计划
    void generateIncomePlan();

    //返回收益
    void generateIncomeBack();
}
