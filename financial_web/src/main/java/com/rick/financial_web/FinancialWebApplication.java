package com.rick.financial_web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDubbo
@EnableSwagger2
public class FinancialWebApplication {


    public static void main(String[] args) {
        SpringApplication.run(FinancialWebApplication.class, args);
    }

}
