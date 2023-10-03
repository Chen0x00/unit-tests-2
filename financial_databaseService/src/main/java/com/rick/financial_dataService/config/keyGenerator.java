package com.rick.financial_dataService.config;


import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class keyGenerator implements KeyGenerator {


    @Override
    public Object generate(Object target, Method method, Object... params) {//参数是spel的内容
        // 构造缓存键
        StringBuilder keyBuilder = new StringBuilder();

        // 将类名和方法名作为缓存键的一部分
        keyBuilder.append(/*target.getClass().getSimpleName()).append(":").append(*/method.getName());

        // 将参数作为缓存键的一部分（这里假设参数是字符串，您可以根据参数类型和需求进行修改）
//        for (Object param : params) {
//            keyBuilder.append(param).append(":");
//        }

        String finalKey = keyBuilder.toString();

        return finalKey;
    }
}
