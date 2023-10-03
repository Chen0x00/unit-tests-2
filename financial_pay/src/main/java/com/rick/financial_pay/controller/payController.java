package com.rick.financial_pay.controller;


import com.rick.financial_api.domain.User;
import com.rick.financial_pay.service.KuiQianService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/kq")
public class payController {

    @Resource
    private KuiQianService kQService;

    /*接收来自vue项目的支付充值请求*/
    @GetMapping("/rece/recharge")
    public String receFrontRechargeKQ(Integer uid,
                                      BigDecimal rechargeMoney,
                                      Model model){
        //默认是错误视图
        String view="err";
        if(uid != null && uid > 0 &&
                rechargeMoney != null && rechargeMoney.doubleValue() > 0 ){
            //1.检查uid是否是有效的用户
            try{
                User user = kQService.queryUser(uid);
                if(user != null ){
                    //创建快钱支付接口需要的请求参数
                    Map<String,String> data = kQService.generateFormData(uid,user.getPhone(),rechargeMoney);
                    model.addAllAttributes(data);
                    //创建充值记录
                    kQService.addRecharge(uid,rechargeMoney,data.get("orderId"));
                    //把订单号存放到redis
                    kQService.addOrderIdToRedis(data.get("orderId"));
                    //提交支付请求给快钱的form页面（thymeleaf）
                    view  = "kqForm";
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return view;

    }


    //接收快钱给商家的支付结果 , 快钱以get方式，发送请求给商家
    @GetMapping("/rece/notify")
    @ResponseBody
    public String payResultNotify(HttpServletRequest request){
        System.out.println("=================接收快钱的异步通知=============");
        kQService.kqNotify(request);
        return "<result>1</result><redirecturl>http://localhost:8080/</redirecturl>";
    }

    //从定时任务，调用的接口
    @GetMapping("/rece/query")
    @ResponseBody
    public String queryKQOrder(){
        kQService.handleQueryOrder();
        return "接收了查询的请求";

    }
}
