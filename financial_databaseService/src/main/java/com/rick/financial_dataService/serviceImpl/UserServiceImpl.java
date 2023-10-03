package com.rick.financial_dataService.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rick.financial_api.common.util.CommonUtil;
import com.rick.financial_api.domain.FinanceAccount;
import com.rick.financial_api.domain.User;
import com.rick.financial_api.pojo.UserAccountInfo;
import com.rick.financial_api.service.UserService;
import com.rick.financial_dataService.mapper.FinanceAccountMapper;
import com.rick.financial_dataService.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ASUS
 * @description 针对表【u_user(用户表)】的数据库操作Service实现
 * @createDate 2023-09-19 16:40:47
 */
@DubboService(interfaceClass = UserService.class, version = "1.0")
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;


    @Value("${financial.pwdSalt}")
    private String pwdSalt;

    @Resource
    private FinanceAccountMapper financeAccountMapper;

    /**
     * 输入手机号和密码登录并返回用户信息且更新登录时间
     */
    @Override
    public User userLogin(String phone, String pwd) {

        User user = null;
        Date date = new Date();
        if (CommonUtil.checkPhoneFormat(phone) && pwd.length() == 32) {
            //密码加盐后才能正确访问数据库
            String loginPwd = DigestUtils.md5Hex(pwd + pwdSalt);
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("phone", phone).eq("login_password", loginPwd);
            user = userMapper.selectOne(userQueryWrapper);
            if (user != null) {//成功查询后更新登录时间
                user.setLastLoginTime(date);
                userMapper.updateById(user);
            }
        }
        return user;
    }

    /**
     * 根据手机号查用户是否存在
     */
    @Override
    public User queryByPhone(String phone) {
        User user = null;
        if (CommonUtil.checkPhoneFormat(phone) == true) {
            user = userMapper.queryByPhone(phone);
        }
        return user;
    }

    /**
     * 根据名字查用户
     */
    @Override
    public User queryByName(String name) {
        User user = null;
        user = baseMapper.queryByName(name);
        return user;
    }

    /**
     * 验证格式后直接注册新用户
     */
    @Override
    public synchronized int userRegister(String phone, String regPwd) {//加上同步锁，防止手机号被多次注册
        int result = 0;
        try {
            //注册密码的二次加密。salt
            String newPwd = DigestUtils.md5Hex(regPwd + pwdSalt);
            System.out.println("加盐后的新密码：" + newPwd);
            //注册User
            User user = new User();
            user.setPhone(phone);
            user.setLoginPassword(newPwd);
            user.setAddTime(new Date());
            userMapper.insert(user);

            //获取插入后的自动生成的主键key，进行开户处理
            FinanceAccount account = new FinanceAccount();
            account.setUid(user.getId());
            account.setAvailableMoney(new BigDecimal("0"));
            financeAccountMapper.insert(account);

            //成功result=1
            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    @Override
    public boolean updateByPhone(String phone, String name, String idCard) {
        int rows = 0;

        if (StringUtils.isAllBlank(phone, name, idCard)) {
            rows = userMapper.updateByPhone(phone, name, idCard);
        }
        return rows > 0;
    }

    /**
     * 查用户和资金
     */
    @Override
    public UserAccountInfo getUserById(Integer uid) {
        UserAccountInfo userAccountInfo = null;
        if (uid != null&& uid>0) {
            userAccountInfo = userMapper.getUMById(uid);
        }
        return userAccountInfo;
    }

}




