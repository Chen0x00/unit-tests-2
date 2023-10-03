package com.rick.financial_api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rick.financial_api.domain.User;
import com.rick.financial_api.pojo.UserAccountInfo;


/**
* @author ASUS
* @description 针对表【u_user(用户表)】的数据库操作Service
* @createDate 2023-09-19 16:40:47
*/
public interface UserService extends IService<User> {

     User userLogin(String phone, String pwd);


    /**
     * 根据手机号查询用户
     */
    User queryByPhone(String phone);

    /**
     * 根据姓名查用户
     */
    User queryByName(String name);

    /**
     * 手机号注册
     */
    int userRegister(String phone, String regPwd);


    boolean updateByPhone(String phone, String name, String idCard);

    UserAccountInfo getUserById(Integer uid);
}
