package com.rick.financial_web.config;



import com.rick.financial_web.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;------用于响应式Web应用程序
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 跨域请求配置类
 */
@Configuration
//@EnableWebMvc，它将禁用Spring Boot的默认Web MVC自动配置，这可能会导致一些问题
public class WebConfig implements WebMvcConfigurer {

    @Value("${JWT.selfKey}")
    private String selfKey;

//    String methods[] = {"GET", "POST", "PUT", "DELETE", "OPTIONS","HEAD"};

    /**
     * 自定义的token拦截器比自带的跨域拦截器执行顺序靠前，当token失效抛出异常的时候，会导致没有进行跨域拦截处理。
     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**")
//                .allowedOriginPatterns("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","HEAD")
//                .allowCredentials(true)
//                .allowedHeaders("*")
//                .maxAge(3600);//一小时
//
//    }

    /**
     * 改为用过滤器方式处理跨域问题
     * 使用的是Spring Framework的CORS支持,tomcat的是javaEE环境下的
     */

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*"); // 允许所有来源的跨域请求
        corsConfiguration.addAllowedHeader("*"); // 允许所有请求头
        corsConfiguration.addAllowedMethod("*"); // 允许所有HTTP方法
        corsConfiguration.setAllowCredentials(true); // 允许发送凭据信息（如Cookie）

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration); // 对所有路径应用上述配置

        return new CorsFilter(urlBasedCorsConfigurationSource); // 创建CorsFilter并返回
    }

    /**
     * 调用自定义的Token拦截器
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        TokenInterceptor tokenInterceptor = new TokenInterceptor(selfKey);
//        String[] addPath = {"/v1/user/realname"};
//        // 将 TokenInterceptor 添加到拦截器链，并指定拦截的路径
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns(addPath);
//    }
}
