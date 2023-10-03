package com.rick.financial_api.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseInfo implements Serializable {

    /**
     * 收益率平均值
     */
    private BigDecimal historyAvgRate;

    /**
     * 注册人数
     */
    private Integer registerUsers;

    /**
     * 累计成交金额
     */
    private BigDecimal sumBigMoney;

    @TableField(exist = false)//表示这些字段在业务逻辑中有用，但不需要存储到数据库中
    private static final long serialVersionUID = 1L;

}
