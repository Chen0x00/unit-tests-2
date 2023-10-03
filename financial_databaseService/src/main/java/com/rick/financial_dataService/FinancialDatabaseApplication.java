package com.rick.financial_dataService;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableDubbo        //开启服务注册
@MapperScan(value = "com.rick.financial_dataService.mapper")
@EnableCaching      //开启缓存
public class FinancialDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialDatabaseApplication.class, args);
    }

}
