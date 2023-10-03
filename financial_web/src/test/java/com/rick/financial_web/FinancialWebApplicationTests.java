package com.rick.financial_web;

import com.rick.financial_api.common.constant.RedisKeyConstant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

@SpringBootTest
class FinancialWebApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {

        Set<ZSetOperations.TypedTuple<String>> range = redisTemplate.boundZSetOps(RedisKeyConstant.KEY_INVEST_RANK).reverseRangeWithScores(0,2);

        for (ZSetOperations.TypedTuple<String> tuple : range) {
            String value = String.valueOf(tuple.getValue());
            double score = tuple.getScore();
            System.out.println("Value: " + value + ", Score: " + score);
        }

    }

}
