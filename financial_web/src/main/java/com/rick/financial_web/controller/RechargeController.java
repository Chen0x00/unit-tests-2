package com.rick.financial_web.controller;


import com.rick.financial_api.common.util.CommonUtil;
import com.rick.financial_api.domain.RechargeRecord;
import com.rick.financial_web.view.PageInfo;
import com.rick.financial_web.view.RespResult;
import com.rick.financial_web.view.recharge.RechargeView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.Oneway;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "用户充值接口")
@RequestMapping(value = "/v1/recharge")
public class RechargeController extends BaseController {


    @ApiOperation(value = "查询某个用户充值信息", notes = "查询最新的六条记录")
    @GetMapping(value = "/info")
    public RespResult queryRechargeInfo(@RequestParam("uid") Integer uid,
                                        @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNO,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "6") Integer pageSize) {
        RespResult respResult = RespResult.ok();
        if (uid != null && uid > 0) {
            long totalCount = rechargeRecordService.queryRecordByUid(uid);
             List<RechargeRecord> rechargeRecords = rechargeRecordService.queryByUid(uid, CommonUtil.PageNo(pageNO), CommonUtil.PageSize(pageSize));
            if (rechargeRecords != null) {
                respResult = RespResult.ok();
                respResult.setList(RechargeToView(rechargeRecords));
                //分页
                PageInfo pageInfo = new PageInfo(pageNO, pageSize, totalCount);
                respResult.setPageInfo(pageInfo);
            }
        }

        return respResult;
    }

    //进行视图转换
    private List<RechargeView> RechargeToView(List<RechargeRecord> rechargeRecords) {
        List<RechargeView> rechargeViews = new ArrayList<>();
        rechargeRecords.forEach(rechargeRecord -> {
            rechargeViews.add(new RechargeView(rechargeRecord));
        });
        return rechargeViews;
    }


}
