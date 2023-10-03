package com.rick.financial_task;

import com.rick.financial_task.task.TaskManager;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;


//开启定时任务
@EnableScheduling
@EnableDubbo
@SpringBootApplication
public class FinancialTaskApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FinancialTaskApplication.class, args);
        TaskManager taskManager = (TaskManager)context.getBean("taskManager");
        taskManager.invokeGenerateIncomePlan();
        taskManager.invokeGenerateIncomeBack();
//        taskManager.invokeReplace();
    }

}
