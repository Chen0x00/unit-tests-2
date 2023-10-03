package com.rick.financial_web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableSwagger2,放在主类更常见
public class Swagger2Config {

    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("光明金融接口文档")
                .description("创造更好的未来")
                .contact(new Contact("rick", "https://github.com/PickleRickofficial", "badtim6657@gamil.com"))
                .license("Apache 2")
                .version("1.0.0")
                .termsOfServiceUrl("https://github.com/PickleRickofficial")
                .build();
    }


    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                //文档信息（全局的）
                .apiInfo(apiInfo())
                //非必要
                .enable(true)
                .groupName("光明金融第一组")
                .host("localhost:8081")
                //.select()：表示进入到 API 选择的配置部分。
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rick.financial_web.controller"))
                .paths(PathSelectors.ant("/v1/**"))//只有路径以 "/v1/" 开头的接口会被包括在文档中。
                .build();
    }


}
