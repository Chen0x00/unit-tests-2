package com.rick.financial_api.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegByPhone implements Serializable {

    private String phone;
    private String smsCode;
    private String regPwd;

    @TableField(exist = false)//表示这些字段在业务逻辑中有用，但不需要存储到数据库中
    private static final long serialVersionUID = 1L;

}
