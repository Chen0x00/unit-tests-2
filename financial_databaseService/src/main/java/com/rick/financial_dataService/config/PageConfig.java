package com.rick.financial_dataService.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分页插件配置
 */
@Configuration
public class PageConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 创建MybatisPlusInterceptor拦截器实例
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 创建PaginationInnerInterceptor分页插件实例
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();

        // 设置数据库类型为MYSQL，以便分页插件知道如何正确生成分页SQL
        paginationInnerInterceptor.setDbType(DbType.MYSQL);

        // 设置是否启用溢出页的模式，当页码小于1或大于总页数时，是否返回第一页或最后一页的数据
        paginationInnerInterceptor.setOverflow(true);

        // 将PaginationInnerInterceptor分页插件添加到MybatisPlusInterceptor拦截器中
        interceptor.addInnerInterceptor(paginationInnerInterceptor);

        // 返回创建好的MybatisPlusInterceptor拦截器实例
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        // 创建ConfigurationCustomizer实例，用于自定义MyBatis的配置
        return configuration -> {
            // 设置不使用废弃的执行器（Executor），这是为了避免警告信息
            configuration.setUseGeneratedKeys(false);
        };
    }

}
