package com.rick.financial_web.view;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * 统一的应答
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class  RespResult {//T表示泛型类，是什么类型的

    //应答码
    private int code;
    //code的文字说明，给用户看的
    private String msg;
    //单个数据
    private Object data;
    //集合数据
    private List list;
    //自定义分页信息~mybatis中是PageInfo，plus是page
    private PageInfo pageInfo;
    //token信息
    private String TokenInfo;






    /**
     * 设置格式
     */
    public static RespResult build(ResultCodeEnum codeEnum){
        RespResult respResult = new RespResult();
        respResult.setCode(codeEnum.getCode());
        respResult.setMsg(codeEnum.getMessage());
        return respResult;
    }

    /**
     * 成功响应
     */
    public static RespResult ok(){
        RespResult respResult = new RespResult();
        return respResult.build(ResultCodeEnum.SUCCESS);
    }

    /**
     * 响应失败
     */
    public static RespResult fail(){
        RespResult respResult = new RespResult();
        return respResult.build(ResultCodeEnum.FAIL);
    }



}
