package com.rick.financial_web.view;

import lombok.Getter;

@Getter
// * 统一返回结果状态信息类
public enum ResultCodeEnum {

    /**应答码
     * 0：默认
     * 1000-2000是请求参数有误，逻辑的问题
     * 2000-3000是服务器请求错误。
     * 3000-4000是访问dubbo的应答结果
     */

    UNKOWN(0,"未知错误,请稍候重试"),
    SUCCESS(200, "请求成功"),
    FAIL(201, "请求失败"),

    /**
     * 产品相关
     */
    REQUEST_PARAM_ERR(1001,"请求参数错误"),
    REQUEST_PRODUCT_TYPE_ERR(1002,"产品类型有误"),
    REQUEST_PRODUCT_OFFLINE(1003, "产品已经下线"),
    /**
     * 注册相关
     */
    SMS_CODE_STILL_AVAILABLE(1004,"手机验证码仍然可以使用" ),
    Phone_FORMAT_REEOR(1005,"手机格式不对"),
    REG_REQUEST_PARAM_ERR(1006,"注册参数不正确"),
    PHONE_EXISTS(1007,"手机号已经存在"),
    /**
     * 登录相关
     */
    USER_NOT_REGISTER(1008, "用户未注册，请先注册"),
    LOGIN_REQUEST_PARAM_ERR(1009,"登录参数不正确"),
    SMS_CODE_INVALID(1010,"验证码过期"),
    TOKEN_INVALID(3000, "Token无效"),
    /**
     * 实名认证相关
     */
    REALNAME_INFO_ERROR(1011, "实名验证的信息有误"),
    REALNAME_SUCCESS(1012, "实名验证通过"),
    REALNAME_ALREADY_EXIT(1013, "用户已经实名"),

    ;


    private Integer code;
    private String message;



    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}






