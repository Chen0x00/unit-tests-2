package com.rick.financial_web.view.recharge;

import com.rick.financial_api.domain.RechargeRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;

@AllArgsConstructor
public class RechargeView {

    private Integer uid;
    private String result="unKnow";
    private String rechargeDate = "-";
    private BigDecimal rechargeMoney;

    public RechargeView(RechargeRecord record) {
        this.uid = record.getUid();
        this.rechargeMoney = record.getRechargeMoney();
        if (record.getRechargeTime() != null) {
            this.rechargeDate = DateFormatUtils.format(record.getRechargeTime(), "yyyy-MM-dd");
        }
        switch (record.getRechargeStatus()) {
            case 0:
                this.result = "充值中";
                break;
            case 1:
                this.result = "充值成功";
                break;
            case 2:
                this.result = "充值失败";
                break;
        }

    }

    public Integer getUid() {
        return uid;
    }

    public String getResult() {
        return result;
    }

    public String getRechargeDate() {
        return rechargeDate;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }
}
