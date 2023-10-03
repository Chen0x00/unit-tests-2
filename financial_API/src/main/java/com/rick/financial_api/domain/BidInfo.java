package com.rick.financial_api.domain;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 投资记录表
 * @TableName b_bid_info
 */

@Data
@NoArgsConstructor
@TableName(value = "b_bid_info")
public class BidInfo implements Serializable {
    /**
     * 投标记录ID
     */
    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;

    /**
     * 产品ID
     */
    private Integer loanId;

    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 投标金额
     */
    private BigDecimal bidMoney;

    /**
     * 投标时间
     */
    private Date bidTime;

    /**
     * 投标状态
     */
    private Integer bidStatus;


    @TableField(exist = false)//在业务逻辑中有用，但不需要存储到数据库中
    private static final long serialVersionUID = 1L;

    public BidInfo(Integer loanId, Integer uid, BigDecimal bidMoney, Date bidTime, Integer bidStatus) {
        this.loanId = loanId;
        this.uid = uid;
        this.bidMoney = bidMoney;
        this.bidTime = bidTime;
        this.bidStatus = bidStatus;
    }


}