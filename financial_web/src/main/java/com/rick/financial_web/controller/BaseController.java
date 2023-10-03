package com.rick.financial_web.controller;


import com.rick.financial_api.service.*;
import com.rick.financial_web.service.SmsService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

//@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)//开启CROS
public class BaseController {

    /**
     * 声明公共的方法，属性等
     */
    //redis公共模板,刚刚忘了注入，导致空值
    @Resource
    protected RedisTemplate redisTemplate;

    @Resource
    protected SmsService smsService;

    //平台信息服务
    @DubboReference(interfaceClass = platBaseInfoService.class,version = "1.0")
    protected platBaseInfoService platBaseInfoService;

    //投标产品服务
    @DubboReference(interfaceClass =LoanInfoService.class,version = "1.0")
    protected LoanInfoService loanInfoService;

    //投资理财服务
    @DubboReference(interfaceClass = InvestInfoService.class,version = "1.0")
    protected InvestInfoService investInfoService;


    //用户服务
    @DubboReference(interfaceClass = UserService.class,version = "1.0")
    protected UserService userService;


    //充值服务
    @DubboReference(interfaceClass = RechargeRecordService.class,version = "1.0")
    protected RechargeRecordService rechargeRecordService;

}
