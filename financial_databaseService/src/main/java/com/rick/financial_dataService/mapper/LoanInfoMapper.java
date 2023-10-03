package com.rick.financial_dataService.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rick.financial_api.domain.LoanInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
*   接口的方法提示只是帮助你自定义方法名，具体的sql语句还是要你自己写
*/
public interface LoanInfoMapper extends BaseMapper<LoanInfo> {

    //利率平均值
    @Select("SELECT AVG(rate) FROM b_loan_info")
    BigDecimal getAverageRate();


    /**
     *更新产品剩余金额
     */
    int updateLeftProductMoney(@Param("loanId") Integer loanId, @Param("money") BigDecimal money);

    //找出满标的理财产品
    List<LoanInfo> queryFullTimeLoan(@Param("begin") Date begin, @Param("end") Date end);

    /*更新产品状态*/
    int updateStatus(@Param("id") Integer id, @Param("productStatusPlan") int productStatusPlan);
}




