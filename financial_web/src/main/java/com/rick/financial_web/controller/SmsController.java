package com.rick.financial_web.controller;


import com.rick.financial_api.common.constant.RedisKeyConstant;
import com.rick.financial_api.common.util.CommonUtil;
import com.rick.financial_api.domain.User;
import com.rick.financial_web.view.RespResult;
import com.rick.financial_web.view.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "手机短信业务接口")
@RestController
@RequestMapping(value = "/v1/code")
public class SmsController extends BaseController{


    @ApiOperation(value = "发送注册验证码短信",notes = "检查手机号和验证码，再发送验证短信")
    @GetMapping(value = "/register")
    public RespResult sendCodeRegister(@RequestParam String phone){
        RespResult respResult = RespResult.fail();
        //验证手机号格式
        if (CommonUtil.checkPhoneFormat(phone)) {
            //判断redis是否有这个手机号的验证码，是否已经发过了
            String key = RedisKeyConstant.KEY_SMS_CODE_REG + phone;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
                //已经发过了
                respResult = RespResult.ok();
                respResult.build(ResultCodeEnum.SMS_CODE_STILL_AVAILABLE);
            }else {
                //数据库查询手机号是否已经注册
                User user = userService.queryByPhone(phone);
                //user==null,手机号是新的
                if (user == null) {
                    boolean send = smsService.sendSMSCode(phone,true);
                    if (send == true) {
                        respResult = RespResult.ok();
                    }
                }
            }
        }else {//手机号格式不对
            respResult.build(ResultCodeEnum.Phone_FORMAT_REEOR);
        }
        return respResult;
    }


    @ApiOperation(value = "发送登录验证码短信",notes = "检查手机号是否已经注册，然后在发送登录短信")
    @GetMapping(value = "/login")
    public RespResult sendCodeLogin(@RequestParam String phone){
        RespResult respResult = RespResult.fail();
        //验证手机号格式
        if (CommonUtil.checkPhoneFormat(phone)) {
            //判断redis是否有这个手机号的验证码，是否已经发过了
            String key = RedisKeyConstant.KEY_SMS_CODE_LOGIN + phone;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
                //已经发过了
                respResult = RespResult.ok();
                respResult.build(ResultCodeEnum.SMS_CODE_STILL_AVAILABLE);
            }else {
                //数据库查询手机号是否已经注册
                User user = userService.queryByPhone(phone);
                //user!=null,手机号已经注册了，存在用户
                if (user != null) {
                    boolean send = smsService.sendSMSCode(phone,false);
                    if (send == true) {
                        respResult = RespResult.ok();
                    }
                }else {//用户为null，提示需要注册
                    respResult.build(ResultCodeEnum.USER_NOT_REGISTER);
                }
            }
        }else {//手机格式未通过
            respResult.build(ResultCodeEnum.Phone_FORMAT_REEOR);
        }
        return respResult;
    }








}
