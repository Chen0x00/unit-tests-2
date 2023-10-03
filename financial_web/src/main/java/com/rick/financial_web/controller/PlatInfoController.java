package com.rick.financial_web.controller;

import com.rick.financial_api.pojo.BaseInfo;
import com.rick.financial_web.view.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "平台基本信息接口")
@RestController
@RequestMapping(value = "/v1/plat")
public class PlatInfoController extends BaseController{

    /**
     * 平台基本信息
     */
    @ApiOperation(value = "平台信息查询方法",notes = "注册人数、平均利率、总投资金额")
    @GetMapping(value = "/info")
    public RespResult queryPlatBaseInfo(){
        BaseInfo baseInfo = platBaseInfoService.queryPlatBaseInfo();
        System.out.println("baseInfo--->>>>>>"+baseInfo);
        RespResult result = new RespResult();
        result.ok();
        result.setData(baseInfo);
        return result;
    }


}
