package com.rick.financial_api.service;

import com.rick.financial_api.pojo.BaseInfo;

public interface platBaseInfoService {
    /**
     * 计算利率，注册人数，累计成交金额
     */
    BaseInfo queryPlatBaseInfo();

}
