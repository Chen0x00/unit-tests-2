package com.rick.financial_dataService.serviceImpl;


import com.rick.financial_api.pojo.BaseInfo;
import com.rick.financial_api.service.BidInfoService;
import com.rick.financial_api.service.LoanInfoService;
import com.rick.financial_api.service.UserService;
import com.rick.financial_api.service.platBaseInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 将缓存注解放在Service层的好处包括：业务逻辑集中，控制缓存粒度，可扩展性，代码重用
 */

@DubboService(interfaceClass = platBaseInfoService.class,version = "1.0")
@Service
@Transactional
@CacheConfig(cacheNames = "platBaseInfo",keyGenerator = "keyGenerator",cacheManager = "cacheManager")
public class  platBaseInfoServiceImpl implements platBaseInfoService {

    @Resource
    private UserService userService;

    @Resource
    private LoanInfoService loanInfoService;

    @Resource
    private BidInfoService bidInfoService;


    /**
     * 历史年化收益率、注册总人数、总投资金额先从缓存中获取
     */
    @Cacheable//查询，更新是@CachePut,删除是@CacheEvict
    @Override
    public BaseInfo queryPlatBaseInfo() {

        //获取注册人数,统计满足查询条件的记录数量,用long是因为够大
        long count = userService.count();
//        System.out.println(count);
        //收益率平均值
        BigDecimal averageRate = loanInfoService.getAverageRate();
        //累计成交金额
        BigDecimal sumBidMoney = bidInfoService.getSumBidMoney();

        BaseInfo baseInfo = new BaseInfo(averageRate, (int) count, sumBidMoney);

        return baseInfo;

    }
}
