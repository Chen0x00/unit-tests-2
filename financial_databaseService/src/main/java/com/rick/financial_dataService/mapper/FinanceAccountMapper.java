package com.rick.financial_dataService.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rick.financial_api.domain.FinanceAccount;
import com.rick.financial_api.domain.IncomeRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;


/**
* @author ASUS
* @description 针对表【u_finance_account(用户财务资金账户表)】的数据库操作Mapper
* @createDate 2023-09-19 16:40:47
* @Entity database.domain.FinanceAccount
*/
public interface FinanceAccountMapper extends BaseMapper<FinanceAccount> {


    /**
     *for update 加上主键表示锁行，不加表示锁表
     */
    FinanceAccount queryByUidForUpdate(@Param("uid") Integer uid);

    /**
     * 更新用户可用金额
     */
    int updateAvailableMoneyWithVersion(@Param("uid") Integer uid, @Param("money") BigDecimal money, @Param("version") Integer version);

    //收益返还，更新可用资金
    int updateAvailableMoneyByIncomeBack(@Param("uid") Integer uid, @Param("bidMoney") BigDecimal bidMoney, @Param("incomeMoney") BigDecimal incomeMoney);

    /*充值更新金额*/
    int updateAvailableMoneyByRecharge(@Param("uid") Integer uid, @Param("rechargeMoney") BigDecimal rechargeMoney);

}




