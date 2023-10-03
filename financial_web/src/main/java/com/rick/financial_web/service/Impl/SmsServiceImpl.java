package com.rick.financial_web.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rick.financial_api.common.constant.RedisKeyConstant;
import com.rick.financial_api.common.util.HttpUtil;
import com.rick.financial_web.properties.AliyunApiSms;
import com.rick.financial_web.service.SmsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private AliyunApiSms aliyunApiSms;

    /**
     * //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
     * headers.put("Authorization", "APPCODE " + appcode);
     * Map<String, String> querys = new HashMap<String, String>();
     * querys.put("mobile", "1343994XXXX");
     * querys.put("content", "【智能云】您的验证码是568126。如非本人操作，请忽略本短信");
     */
    @Override
    public boolean sendSMSCode(String phone, boolean regOrLogin) {
        boolean send = false;
        //设置短信内容
        String code = RandomStringUtils.randomNumeric(6);
        String url = null;
        /**
         * true为注册短信，false为登录短信
         */
        //接口已经验证通过，直接用假数据验证其他接口

        /*if (regOrLogin == true) {
            //更新content的%S 【光明金融】你的注册验证码是：%s,3分钟内有效，请勿泄露给他人。如非本人操作，请忽略本短信
            String content = String.format(aliyunApiSms.getRegisterContent(), code);
            System.out.println(content);
            //设置url
//        url = aliyunApiSms.getUrl() + "?mobile=" + phone + "&content=" + content;
            url = aliyunApiSms.getUrl() + "?mobile=" + phone + "&content=【智能云】您的验证码是200002。如非本人操作，请忽略本短信";
        } else {
            String content = String.format(aliyunApiSms.getLoginContent(), code);
            System.out.println(content);
            //设置url
//        url = aliyunApiSms.getUrl() + "?mobile=" + phone + "&content=" + content;
            url = aliyunApiSms.getUrl() + "?mobile=" + phone + "&content=【智能云】您的验证码是100001。如非本人操作，请忽略本短信";
        }


        //get方法
        try {
            CloseableHttpResponse response = HttpUtil.sendGet(url, aliyunApiSms.getAppcode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //得到返回的数据 {"error_code":0,"reason":"成功","result":"6431775"}
                String json = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println("得到返回的数据：" + json);
                //解析数据
                if (StringUtils.isNotBlank(json)) {
                    JSONObject jsonObject = JSON.parseObject(json);
                    //是否成功
                    if ("0".equals(jsonObject.getString("error_code")) && "成功".equals(jsonObject.getString("reason"))) {
                        send = true;
                        //验证码存到redis，等下验证
                        String key = RedisKeyConstant.KEY_SMS_CODE_REG + phone;
                        redisTemplate.boundValueOps(key).set(code, 3, TimeUnit.MINUTES);

                    }
                }
            }
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        send = true;
        String key;
        //验证码存到redis，等下验证
        if (regOrLogin == true) {
             key = RedisKeyConstant.KEY_SMS_CODE_REG + phone;
        }else {
             key = RedisKeyConstant.KEY_SMS_CODE_LOGIN + phone;
        }

        redisTemplate.boundValueOps(key).set(code, 3, TimeUnit.MINUTES);

        return send;
    }

    /**
     * 从redis中查看验证码是否正确和过期
     */
    @Override
    public boolean checkCodeAndPhone(String code, String phone,boolean regOrLogin) {
        /**
         * true为注册短信，false为登录短信
         */
        String key;
        if (regOrLogin == true) {
            key = RedisKeyConstant.KEY_SMS_CODE_REG + phone;
        }else {
            key = RedisKeyConstant.KEY_SMS_CODE_LOGIN + phone;
        }
        if (redisTemplate.hasKey(key)) {//没过期
            String rightCode = (String) redisTemplate.boundValueOps(key).get();
            if (rightCode.equals(code)) {//验证码正确
                return true;
            }
        }
        return false;
    }


}
