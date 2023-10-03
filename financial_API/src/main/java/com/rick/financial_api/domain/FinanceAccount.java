package com.rick.financial_api.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户财务资金账户表
 * @TableName u_finance_account
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "u_finance_account")
public class FinanceAccount implements Serializable {

    private Integer id;

    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 用户可用资金
     */
    private BigDecimal availableMoney;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;


    @TableField(exist = false)//表示这些字段在业务逻辑中有用，但不需要存储到数据库中
    private static final long serialVersionUID = 1L;


    public FinanceAccount(Integer id, Integer uid, BigDecimal availableMoney) {
        this.id = id;
        this.uid = uid;
        this.availableMoney = availableMoney;
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
        FinanceAccount other = (FinanceAccount) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getAvailableMoney() == null ? other.getAvailableMoney() == null : this.getAvailableMoney().equals(other.getAvailableMoney()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getAvailableMoney() == null) ? 0 : getAvailableMoney().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", availableMoney=").append(availableMoney);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}