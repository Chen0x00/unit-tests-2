package com.rick.financial_dataService.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rick.financial_api.domain.User;
import com.rick.financial_api.pojo.UserAccountInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
* @author ASUS
* @description 针对表【u_user(用户表)】的数据库操作Mapper
* @createDate 2023-09-19 16:40:47
* @Entity database.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据手机号查用户
     */
    @Select("select * from u_user where phone=#{phone}")
    User queryByPhone(@Param("phone") String phone);

    /**
     * 根据name查用户
     */
    @Select("select * from u_user where name=#{name}")
    User queryByName(@Param("name") String name);


    @Update("update u_user set name=#{name},id_card={idCard} where phone=#{phone}")
    int updateByPhone(@Param("phone") String phone, @Param("name") String name,@Param("idCard") String idCard);

    /**
     * 根据uid查用户和资金
     */
    UserAccountInfo getUMById(@Param("uid") Integer uid);

}




