package com.rick.financial_web.controller;


import com.rick.financial_api.common.util.CommonUtil;
import com.rick.financial_api.common.util.JwtUtil;
import com.rick.financial_api.domain.User;
import com.rick.financial_api.pojo.RealNameVo;
import com.rick.financial_api.pojo.RegByPhone;
import com.rick.financial_api.pojo.UserAccountInfo;
import com.rick.financial_api.service.UserService;
import com.rick.financial_web.service.Impl.RealNameServiceImpl;
import com.rick.financial_web.view.RespResult;
import com.rick.financial_web.view.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@Api(tags = "用户信息接口")
@RequestMapping(value = "/v1/user")
public class UserController extends BaseController {

    @Value("${JWT.selfKey}")
    private String jwtKey;

    @Resource
    private RealNameServiceImpl realNameService;


    @ApiOperation(value = "手机号注册功能", notes = "注册参数：手机号+验证码+密码")
    @PostMapping("/register")
    public RespResult UserRegisterByPhone(@RequestBody RegByPhone regByPhone) {
        RespResult respResult = RespResult.fail();
        //1.手机号为新且格式正确，密码格式正确，验证码正确
        if (CommonUtil.checkPhoneFormat(regByPhone.getPhone()) &&
                regByPhone.getRegPwd().length() == 32 &&
                userService.queryByPhone(regByPhone.getPhone()) == null &&
                smsService.checkCodeAndPhone(regByPhone.getSmsCode(), regByPhone.getPhone(), true)) {
            int regResult = userService.userRegister(regByPhone.getPhone(), regByPhone.getRegPwd());
            if (regResult == 1) {
                respResult = RespResult.ok();
            }
        } else {//验证不过关
            respResult.build(ResultCodeEnum.REG_REQUEST_PARAM_ERR);
        }
        return respResult;
    }


    /**
     * 登录，获取token-jwt
     */
    @ApiOperation(value = "用户登录-获取访问Token", notes = "验证手机和密码后进行登录并返回token")
    @PostMapping("/login")
    public RespResult userLogin(@RequestParam String phone,
                                @RequestParam String pwd,
                                @RequestParam String smsCode) {
        RespResult respResult = RespResult.fail();
        if (CommonUtil.checkPhoneFormat(phone) && pwd.length() == 32) {
            if (smsService.checkCodeAndPhone(smsCode, phone, false)) {//检查验证码是否过期
                //格式通过后查询用户并返回用户信息给前端
                User user = userService.userLogin(phone, pwd);
                if (user != null) {//成功查询并返回
                    //加入data
                    HashMap<String, Object> tokenData = new HashMap<>();
                    tokenData.put("uid", user.getId());
                    //生成token返回前端
                    JwtUtil jwtUtil = new JwtUtil(jwtKey);
                    String token = jwtUtil.generateToken(tokenData, 150);
                    //生成成功
                    respResult = RespResult.ok();
                    respResult.setTokenInfo(token);

                    //返回用户基本信息
                    HashMap<String, Object> userInfo = new HashMap<>();
                    userInfo.put("uid", user.getId());
                    userInfo.put("phone", user.getPhone());
                    userInfo.put("name", user.getName());
                    respResult.setData(userInfo);
                } else {//查询用户失败，用户为空
                    respResult.build(ResultCodeEnum.USER_NOT_REGISTER);
                }
            } else {//验证码过期
                respResult.build(ResultCodeEnum.SMS_CODE_INVALID);
            }
        } else {//手机格式或者密码格式不对，返回笼统的回答
            respResult.build(ResultCodeEnum.LOGIN_REQUEST_PARAM_ERR);
        }//返回默认失败
        return respResult;
    }

    /**
     * 实名认证
     */
    @ApiOperation(value = "实名验证方法", notes = "需要提供手机号和姓名已经身份证号")
    @PostMapping(value = "/realname")
    public RespResult RealName(@RequestBody RealNameVo realNameVo) {
        RespResult respResult = RespResult.fail();
        //验证参数
        if (CommonUtil.checkPhoneFormat(realNameVo.getPhone())) {//手机
            if (StringUtils.isNotBlank(realNameVo.getName()) && StringUtils.isNotBlank(realNameVo.getIdCard())) {//身份信息
                User user = userService.queryByName(realNameVo.getName());
                if (user == null) {//未实名
                    //开始实名
                    boolean result = realNameService.handleRealName(realNameVo.getPhone(), realNameVo.getName(), realNameVo.getIdCard());
                    if (result) {
                        respResult = RespResult.ok();
                        respResult.setData(realNameVo.getName());
                    }
                } else {//已经实名
                    respResult.build(ResultCodeEnum.REALNAME_ALREADY_EXIT);
                }
            } else {//信息不对
                respResult.build(ResultCodeEnum.REALNAME_INFO_ERROR);
            }
        } else {//手机格式不对
            respResult.build(ResultCodeEnum.Phone_FORMAT_REEOR);
        }

        return respResult;

    }

    /**
     * 用户中心
     */
    @ApiOperation(value = "用户中心", notes = "提供用户的各种信息")
    @ApiImplicitParam(name = "uid", value = "用户登录获取请求头中的uid")
    @GetMapping("/userCenter")
    public RespResult userCenter(@RequestHeader(value = "uid", required = false) Integer uid) {
        RespResult respResult = RespResult.fail();

        if (uid > 0 && uid != null) {
            UserAccountInfo userAccountInfo = userService.getUserById(uid);
            if (userAccountInfo != null) {
                respResult = RespResult.ok();
                //返回用户信息
                HashMap<String, Object> data = new HashMap<>();
                data.put("name", userAccountInfo.getName());
                data.put("phone", userAccountInfo.getPhone());
                data.put("headerUrl", userAccountInfo.getHeaderImage());
                data.put("money", userAccountInfo.getAvailableMoney());
                data.put("addTime", DateFormatUtils.format(userAccountInfo.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
                if (userAccountInfo.getLastLoginTime() != null) {
                    data.put("loginTime", DateFormatUtils.format(userAccountInfo.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss"));
                } else {
                    data.put("loginTime", "-");
                }
                respResult.setData(data);
            }
        }
        return respResult;
    }


}
