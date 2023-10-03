package com.rick.financial_api.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName u_user
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "u_user")
public class User implements Serializable {
    /**
     * 用户ID，主键
     */
    private Integer id;

    /**
     * 注册手机号码
     */
    private String phone;

    /**
     * 登录密码，密码长度最大16位
     */
    private String loginPassword;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户身份证号码
     */
    private String idCard;

    /**
     * 注册时间
     */
    private Date addTime;

    /**
     * 最近登录时间
     */
    private Date lastLoginTime;

    /**
     * 用户头像文件路径
     */
    private String headerImage;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;


    @TableField(exist = false)//表示这些字段在业务逻辑中有用，但不需要存储到数据库中
    private static final long serialVersionUID = 1L;

    public User(Integer id, String phone, String loginPassword, String name, String idCard, Date addTime, Date lastLoginTime, String headerImage) {
        this.id = id;
        this.phone = phone;
        this.loginPassword = loginPassword;
        this.name = name;
        this.idCard = idCard;
        this.addTime = addTime;
        this.lastLoginTime = lastLoginTime;
        this.headerImage = headerImage;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getLoginPassword() == null ? other.getLoginPassword() == null : this.getLoginPassword().equals(other.getLoginPassword()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getIdCard() == null ? other.getIdCard() == null : this.getIdCard().equals(other.getIdCard()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getLastLoginTime() == null ? other.getLastLoginTime() == null : this.getLastLoginTime().equals(other.getLastLoginTime()))
            && (this.getHeaderImage() == null ? other.getHeaderImage() == null : this.getHeaderImage().equals(other.getHeaderImage()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getLoginPassword() == null) ? 0 : getLoginPassword().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getIdCard() == null) ? 0 : getIdCard().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getLastLoginTime() == null) ? 0 : getLastLoginTime().hashCode());
        result = prime * result + ((getHeaderImage() == null) ? 0 : getHeaderImage().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", phone=").append(phone);
        sb.append(", loginPassword=").append(loginPassword);
        sb.append(", name=").append(name);
        sb.append(", idCard=").append(idCard);
        sb.append(", addTime=").append(addTime);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", headerImage=").append(headerImage);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}