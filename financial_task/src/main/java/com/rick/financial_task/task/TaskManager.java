package com.rick.financial_task.task;


import com.rick.financial_api.common.util.HttpUtil;
import com.rick.financial_api.service.IncomeRecordService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

@Component(value = "taskManager")
public class TaskManager {

    @DubboReference(interfaceClass = IncomeRecordService.class, version = "1.0")
    private IncomeRecordService incomeRecordService;

    //生成收益计划
    @Scheduled(cron = "0 0 0 1 * ?") // 每个月的第一天的0:00
    public void invokeGenerateIncomePlan(){
        incomeRecordService.generateIncomePlan();
    }
    //返回收益
    @Scheduled(cron = "5 0 0 1 * ?") // 每个月的第一天的0:05
    public void invokeGenerateIncomeBack(){
        incomeRecordService.generateIncomeBack();
    }

    /**
     * 补单接口,隔一段时间去问询接口
     */
    public void invokeReplace() {
        try{
            String url = "http://localhost:9000/pay/kq/rece/query";
            HttpUtil.sendGet(url,null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
