package com.rick.financial_web.service;

import org.apache.ibatis.annotations.Param;

public interface SmsService {

    boolean sendSMSCode(String phone,boolean regOrLogin);

    boolean checkCodeAndPhone(@Param("code") String code, @Param("phone") String phone,@Param("regOrLogin") boolean regOrLogin);


}