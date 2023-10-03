package com.rick.financial_web.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.rick.financial_api.common.util.JwtUtil;
import com.rick.financial_web.view.RespResult;
import com.rick.financial_web.view.ResultCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class TokenInterceptor implements HandlerInterceptor {

    private String secretKey;

    public TokenInterceptor(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.如果是option请求直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        boolean flag = false;

        try {
            //获取请求头的数据
            String uid = request.getHeader("uid");
            String token = request.getHeader("Authorization");

            if (StringUtils.isNotBlank(token)) {
                //Bearer
                String jwt = token.substring(7);
                //验证jwt完整性
                JwtUtil jwtUtil = new JwtUtil(secretKey);
                if (jwtUtil.verifyToken(jwt) == true) {
                    //读取jwt数据，与uid对比
                    Map<String, Claim> claimMap = jwtUtil.decodeToken(jwt);
                    String jwtUid = String.valueOf(claimMap.get(uid));
                    //相同则放行
                    if (jwtUid.equals(uid)) {
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            flag = false;
            System.out.println("Token无效，请重试"+e.getMessage());
        }
        if (!flag) {
            // 如果验证失败，返回提示信息
            RespResult respResult = RespResult.fail();
            respResult.build(ResultCodeEnum.TOKEN_INVALID);

            String jsonString = JSONObject.toJSONString(respResult);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(jsonString);
            writer.flush();
            writer.close();
        }

        return flag;
    }
}
