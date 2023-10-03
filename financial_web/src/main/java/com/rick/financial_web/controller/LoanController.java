package com.rick.financial_web.controller;

import com.rick.financial_api.common.util.CommonUtil;
import com.rick.financial_api.domain.LoanInfo;
import com.rick.financial_api.pojo.BidAndLoan;
import com.rick.financial_web.view.PageInfo;
import com.rick.financial_web.view.RespResult;
import com.rick.financial_web.view.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "投标产品信息接口")
@RestController
@RequestMapping(value = "/v1/product")
public class LoanController extends BaseController {


    @ApiOperation(value = "首页三类产品", notes = "新手标And优选标And散标")
    @GetMapping(value = "/index")
    public RespResult queryProductIndex() {
        RespResult respResult = new RespResult();

        List<LoanInfo> loanInfos = loanInfoService.multipleLoan();
        if (loanInfos != null) {
            respResult.ok();
            respResult.setList(loanInfos);
        } else {
            respResult.fail();
        }
        return respResult;
    }

    @ApiOperation(value = "查询某类产品", notes = "新手标or优选标or散标")
    @GetMapping(value = "/type")
    public RespResult queryProductByType(@RequestParam(value = "pType", defaultValue = "0") Integer pType,
                                         @RequestParam(value = "pageNO", required = false, defaultValue = "1") Integer pageNo,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "9") Integer pageSize
    ) {
        RespResult respResult = RespResult.fail();
        if (pType != null && (pType >= 0 || pType < 3)) {
            //总记录数
            long records = loanInfoService.queryRecordByType(pType);
            if (records > 0) {
                //某类产品
                List<LoanInfo> loanInfos = loanInfoService.queryByTypeLimit(pType, CommonUtil.PageNo(pageNo), CommonUtil.PageSize(pageSize));
                //返回分页信息和产品信息
                PageInfo pageInfo = new PageInfo(pageNo, pageSize, records);

                respResult = RespResult.ok();
                respResult.setList(loanInfos);
                respResult.setPageInfo(pageInfo);
            } else {
                //请求参数错误
                respResult = RespResult.build(ResultCodeEnum.REQUEST_PARAM_ERR);
            }
        }
        return respResult;

    }

    @ApiOperation(value = "产品详情和投资记录", notes = "查询某个产品的详细信息和投资者的信息")
    @GetMapping(value = "/info")
    public RespResult queryProductDetail(@RequestParam("productId") Integer id) {

        RespResult respResult = RespResult.fail();
        if (id != null && id > 0) {
            //调用产品查询
            LoanInfo loanInfo = loanInfoService.queryById(id);
            if (loanInfo != null) {
                //查询投资记录
                List<BidAndLoan> blByLoanId = investInfoService.getBLByLoanId(id, 1, 5);
                //查询成功
                respResult = RespResult.ok();
                respResult.setData(loanInfo);
                respResult.setList(blByLoanId);
            }else {//产品信息为空，表示下线
               return respResult.build(ResultCodeEnum.REQUEST_PRODUCT_OFFLINE);
            }
        }

        return respResult;
    }


}
