package com.rick.financial_api.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 收益记录表
 * @TableName b_income_record
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "b_income_record")
public class IncomeRecord implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 产品ID
     */
    private Integer loanId;

    /**
     * 投标记录ID
     */
    private Integer bidId;

    /**
     * 投资金额
     */
    private BigDecimal bidMoney;

    /**
     * 收益时间
     */
    private Date incomeDate;

    /**
     * 收益金额
     */
    private BigDecimal incomeMoney;

    /**
     * 收益状态（0未返，1已返）
     */
    private Integer incomeStatus;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    @TableField(exist = false)//表示这些字段在业务逻辑中有用，但不需要存储到数据库中
    private static final long serialVersionUID = 1L;





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
        IncomeRecord other = (IncomeRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getLoanId() == null ? other.getLoanId() == null : this.getLoanId().equals(other.getLoanId()))
            && (this.getBidId() == null ? other.getBidId() == null : this.getBidId().equals(other.getBidId()))
            && (this.getBidMoney() == null ? other.getBidMoney() == null : this.getBidMoney().equals(other.getBidMoney()))
            && (this.getIncomeDate() == null ? other.getIncomeDate() == null : this.getIncomeDate().equals(other.getIncomeDate()))
            && (this.getIncomeMoney() == null ? other.getIncomeMoney() == null : this.getIncomeMoney().equals(other.getIncomeMoney()))
            && (this.getIncomeStatus() == null ? other.getIncomeStatus() == null : this.getIncomeStatus().equals(other.getIncomeStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getLoanId() == null) ? 0 : getLoanId().hashCode());
        result = prime * result + ((getBidId() == null) ? 0 : getBidId().hashCode());
        result = prime * result + ((getBidMoney() == null) ? 0 : getBidMoney().hashCode());
        result = prime * result + ((getIncomeDate() == null) ? 0 : getIncomeDate().hashCode());
        result = prime * result + ((getIncomeMoney() == null) ? 0 : getIncomeMoney().hashCode());
        result = prime * result + ((getIncomeStatus() == null) ? 0 : getIncomeStatus().hashCode());
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
        sb.append(", loanId=").append(loanId);
        sb.append(", bidId=").append(bidId);
        sb.append(", bidMoney=").append(bidMoney);
        sb.append(", incomeDate=").append(incomeDate);
        sb.append(", incomeMoney=").append(incomeMoney);
        sb.append(", incomeStatus=").append(incomeStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}