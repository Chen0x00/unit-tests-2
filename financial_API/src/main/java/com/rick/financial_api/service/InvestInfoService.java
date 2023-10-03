package com.rick.financial_api.service;

import com.rick.financial_api.pojo.BidAndLoan;

import java.math.BigDecimal;
import java.util.List;

/**
 * 投资消息接口
 */
public interface InvestInfoService {

    //某个产品的投资记录
    List<BidAndLoan> getBLByLoanId(Integer loanId, Integer pageNo, Integer pageSize);

    /**
     *根据用户id和loanId购买产品，返回1代表成功
     */
    int investProduct(Integer uid, Integer loanId, BigDecimal money);

}
