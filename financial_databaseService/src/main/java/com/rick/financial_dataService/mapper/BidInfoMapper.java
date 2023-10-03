package com.rick.financial_dataService.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rick.financial_api.domain.BidInfo;
import com.rick.financial_api.pojo.BidAndLoan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author ASUS
 * @description 针对表【b_bid_info(投资记录表)】的数据库操作Mapper
 * @createDate 2023-09-19 16:40:46
 * @Entity database.domain.BidInfo
 */

public interface BidInfoMapper extends BaseMapper<BidInfo> {

    //累计成交金额
    @Select(value = "select sum(bid_money) as sumBidMoney from b_bid_info")
    BigDecimal getSumBidMoney();

    //分页查询对应的投资记录和手机号
    List<BidAndLoan> getBLByLoanId(@Param("loanId") Integer loanId,
                                   @Param("offset") Integer offset,
                                   @Param("pageSize") Integer pageSize);

    //查询满标投资记录
    List<BidInfo> getByLoanId(@Param("loanId") Integer loanId);


}




