package com.rick.financial_api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rick.financial_api.domain.BidInfo;

import java.math.BigDecimal;


/**
* @author ASUS
* @description 针对表【b_bid_info(投资记录表)】的数据库操作Service
* @createDate 2023-09-19 16:40:47
*/
public interface BidInfoService extends IService<BidInfo> {

    BigDecimal getSumBidMoney();
}
