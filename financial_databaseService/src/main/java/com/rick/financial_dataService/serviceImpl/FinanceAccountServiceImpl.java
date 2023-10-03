package com.rick.financial_dataService.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rick.financial_api.domain.FinanceAccount;
import com.rick.financial_api.service.FinanceAccountService;
import com.rick.financial_dataService.mapper.FinanceAccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author ASUS
* @description 针对表【u_finance_account(用户财务资金账户表)】的数据库操作Service实现
* @createDate 2023-09-19 16:40:47
*/
//@DubboService
@Service
@Transactional
public class FinanceAccountServiceImpl extends ServiceImpl<FinanceAccountMapper, FinanceAccount>
    implements FinanceAccountService {


}




