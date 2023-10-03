package com.rick.financial_web.controller;

import com.rick.financial_api.common.constant.RedisKeyConstant;
import com.rick.financial_api.common.util.CommonUtil;
import com.rick.financial_api.pojo.UserAccountInfo;
import com.rick.financial_web.view.RespResult;
import com.rick.financial_web.view.ResultCodeEnum;
import com.rick.financial_web.view.invest.RankView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;


@Api(tags = "投资理财消费接口")
@RestController
@RequestMapping(value = "/v1/invest")
public class InvestController extends BaseController {

    /**
     * 通过redis查
     */
    @ApiOperation(value = "投资排行榜", notes = "显示投资金额最高的三个水鱼信息")
    @GetMapping(value = "/rank")
    public RespResult showInvestRank() {

        RespResult respResult = RespResult.fail();
        //从redis中根据分数逆序查数据,这里要声明ZSetOperations，除非下面用for（:）
        Set<ZSetOperations.TypedTuple<String>> range = redisTemplate.boundZSetOps(RedisKeyConstant.KEY_INVEST_RANK).reverseRangeWithScores(0, 2);//注意不要写出by

        if (!range.isEmpty()) {
            ArrayList<RankView> rankViews = new ArrayList<>();
        /*
        tuple 是有序集合（ZSet）中的元素，它包含两个部分：成员（member）和分数（score）
         */
            range.forEach(tuple -> {
                rankViews.add(new RankView(CommonUtil.desensitize(String.valueOf(tuple.getValue())), tuple.getScore()));// toString()是输出方法，不是类型转换方法

            });
            respResult = RespResult.ok();
            respResult.setList(rankViews);
        } else {
            return respResult;
        }

        return respResult;
    }


    /**
     * 购买理财产品,更新投资排行榜
     */
    @ApiOperation(value = "购买理财产品", notes = "根据产品id来消费")
    @PostMapping("/consumeProduct")
    public RespResult investProduct(@RequestParam("uid") Integer uid,
                                    @RequestParam("loanId") Integer loanId,
                                    @RequestParam("money") BigDecimal money) {
        RespResult respResult = RespResult.fail();
        //检查参数
        if ((uid != null && uid > 0) && (loanId != null && loanId > 0) && (money != null && money.doubleValue() >= 100.00)) {
            //购买产品
            int investResult=investInfoService.investProduct(uid,loanId,money);
            /**
             * 购买理财产品,0为参数不正确，1为成功，2为账户不存在，3为用户可用余额不足，4为产品不存在，5为投资金额超过投标要求
             */
            switch (investResult) {
                case 0:
                    respResult.setMsg("参数不正确");
                    break;
                case 1:
                    respResult = RespResult.ok();
                    //更新投资排行榜
                    updateInvestRank(uid,money);
                    break;
                case 2:
                    respResult.setMsg("账户不存在");
                    break;
                case 3:
                    respResult.setMsg("用户余额不足");
                    break;
                case 4:
                    respResult.setMsg("产品不存在");
                    break;
                case 5:
                    respResult.setMsg("投资金额超过投标要求");
                    break;
            }
        }

        return respResult;

    }

    /**
     * 更新投资排行榜
     */
    private void updateInvestRank(Integer uid,BigDecimal money){
        UserAccountInfo user = userService.getUserById(uid);
        if (user != null) {
            //更新
            String key = RedisKeyConstant.KEY_INVEST_RANK;
            redisTemplate.boundZSetOps(key).incrementScore(user.getPhone(), money.doubleValue());
        }
    }


}
