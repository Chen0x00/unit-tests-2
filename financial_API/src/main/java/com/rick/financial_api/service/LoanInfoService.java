package com.rick.financial_api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rick.financial_api.domain.LoanInfo;
import com.rick.financial_api.pojo.BidAndLoan;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;


/**
* @author ASUS
* @description 针对表【b_loan_info(产品信息表)】的数据库操作Service
* @createDate 2023-09-19 16:40:47
*/
public interface LoanInfoService extends IService<LoanInfo> {

    //平均利率
    BigDecimal getAverageRate();

    //根据类型分页查单个产品
    List<LoanInfo> queryByTypeLimit(Integer pType,Integer pageNo,Integer pageSize);

    //分页查三个三个产品
    List<LoanInfo> multipleLoan();

    //某个产品的总记录数
    long queryRecordByType(Integer pType);

    //id查产品
    LoanInfo queryById(Integer Id);




}
