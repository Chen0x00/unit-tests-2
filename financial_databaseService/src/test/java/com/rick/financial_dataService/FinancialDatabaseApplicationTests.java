package com.rick.financial_dataService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.financial_api.domain.BidInfo;
import com.rick.financial_api.domain.LoanInfo;
import com.rick.financial_api.domain.RechargeRecord;
import com.rick.financial_api.domain.User;
import com.rick.financial_api.pojo.BidAndLoan;
import com.rick.financial_api.pojo.UserAccountInfo;
import com.rick.financial_api.service.*;
import com.rick.financial_dataService.mapper.UserMapper;
import com.rick.financial_dataService.serviceImpl.UserServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


@SpringBootTest
class FinancialDatabaseApplicationTests {

    /**
     * 必须通过service层调用impl层
     */
    @Resource
    private platBaseInfoService platService;

    @Resource
    private LoanInfoService loanInfoService;

    @Resource
    private InvestInfoService investInfoService;

    @Resource
    private UserServiceImpl userServiceimpl;
    @Resource
    private UserMapper userMapper;

    @Resource
    private RechargeRecordService rechargeRecordService;

    @Resource
    private IncomeRecordService incomeRecordService;

    @Test
    void contextLoads() {
        System.out.println("platBaseInfo------->>>>"+platService.queryPlatBaseInfo());
        System.out.println("----------验证缓存是否生效----------");
        System.out.println("platBaseInfo------->>>>"+platService.queryPlatBaseInfo());
        System.out.println("platBaseInfo------->>>>"+platService.queryPlatBaseInfo());
        System.out.println("platBaseInfo------->>>>"+platService.queryPlatBaseInfo());

    }

    @Test
    void pageTest1(){
        // 创建分页对象，并设置分页信息（页码为1，每页显示10条记录）
        Page<LoanInfo> page = new Page<>(1, 10);

        // 调用自定义的分页查询方法
        Page<LoanInfo> resultPage = loanInfoService.page(page);

        // 从分页对象中获取查询结果
        List<LoanInfo> resultList = resultPage.getRecords(); // 获取查询结果列表
        System.out.println(resultList);
        long totalRecords = resultPage.getTotal(); // 获取总记录数
        System.out.println(totalRecords);
    }

    @Test
    void pageTest02(){
        // 创建分页对象，并设置分页信息（页码为1，每页显示10条记录）
        Page<LoanInfo> page = new Page<>(1, 10);
        QueryWrapper<LoanInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("release_time");


        // 调用自定义的分页查询方法
        Page<LoanInfo> resultPage = loanInfoService.page(page,queryWrapper);

        // 从分页对象中获取查询结果
        List<LoanInfo> resultList = resultPage.getRecords(); // 获取查询结果列表
        System.out.println(resultList);
        long totalRecords = resultPage.getTotal(); // 获取总记录数
        System.out.println(totalRecords);
    }

    @Test
    void pageTest03(){

        List<LoanInfo> loanInfos = loanInfoService.queryByTypeLimit(2, 1, 5);
        System.out.println(loanInfos);

    }


    @Test
    public void queryMapper() {
        System.out.println(loanInfoService.queryById(1));
        List<BidAndLoan> blByLoanId = investInfoService.getBLByLoanId(7,1,5);
        System.out.println(blByLoanId);

    }



    @Test
    public void queryMapper2() {
//        User user = userServiceimpl.queryByPhone("13700000000");
//        System.out.println("user--->>>>>>>"+user);
//        User user = userMapper.queryByPhone("13700000000");
//        System.out.println("user--->>>>>>>"+user);

//        User user = userMapper.queryByName("李四");
//        System.out.println(user);

//        UserAccountInfo accountInfo = userMapper.getUMById(1);
//        System.out.println(accountInfo);

        List<RechargeRecord> rechargeRecords = rechargeRecordService.queryByUid(1, 1, 6);
        rechargeRecords.forEach(System.out::println);



    }


    @Test
    public void PwdSalt(){
        //6d3fa26ee93e491079a75f3cf630d135
//        String pwd = DigestUtils.md2Hex("skjdlakd544365");
//        System.out.println(pwd);
//
        userServiceimpl.userRegister("546464654654", "464das4d65a4sd654asd4das44d54sad");
        //82452c1a8cf452cec969a76d510a8be8
        //82452c1a8cf452cec969a76d510a8be8
    }

    @Test
    void investProduct(){
        /**
         * 购买理财产品,0为参数不正确，1为成功，2为账户不存在，3为用户可用余额不足，4为产品不存在，5为投资金额超过投标要求
         */
        int result = investInfoService.investProduct(1, 1310699, new BigDecimal("2000"));
        System.out.println("返回的结果是：--》》》"+result);
//        BidInfo bidInfo = new BidInfo(1, 2, new BigDecimal("1"), new Date(), 1);
//        System.out.println(bidInfo);


    }


    //测试task
    @Test
    void TestTask() {
//        incomeRecordService.generateIncomePlan();
        incomeRecordService.generateIncomeBack();
    }




}
