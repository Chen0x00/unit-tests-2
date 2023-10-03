package com.rick.financial_web.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rick.financial_api.common.util.HttpUtil;
import com.rick.financial_api.service.UserService;
import com.rick.financial_web.properties.RealNamePro;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class RealNameServiceImpl {

    @Resource
    private RealNamePro realNamePro;

    @DubboReference(interfaceClass = UserService.class, version = "1.0")
    private UserService userService;


    /**
     * 处理实名认证
     */
    public boolean handleRealName(String phone, String name, String idCard) {
        boolean flag = false;
        //请求体存放数据
        HashMap<String, String> bodys = new HashMap<String, String>();
        bodys.put("idcard", idCard);
        bodys.put("mobile", phone);
        bodys.put("name", name);

        try {
            /*CloseableHttpResponse response = HttpUtil.sendPost(realNamePro.getUrl(), realNamePro.getAppcode(), bodys);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String json = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(json);*/
            String json = "{\n" +
                    "    \"code\" : \"0\",\n" +
                    "    \"message\" : \"成功\",\n" +
                    "    \"result\" : {\n" +
                    "        \"name\" : \"冯一枫\", //姓名\n" +
                    "        \"mobile\" : \"18011223678\", //手机号\n" +
                    "        \"idcard\" : \"350301191212222329422\", //身份证号\n" +
                    "        \"res\" : \"1\", //验证结果   1:一致  2:不一致  3:无记录  -1:异常\n" +
                    "        \"description\" : \"一致\",   // 验证结果状态描述（与res状态码相对应）\n" +
                    "        \"sex\": \"男\",\n" +
                    "        \"birthday\": \"19930123\",\n" +
                    "        \"address\": \"江西省遂川县\" \n" +
                    "    }\n" +
                    "}";

            //解析数据
            if (StringUtils.isNotBlank(json)) {
                JSONObject jsonObject = JSON.parseObject(json);
                if (jsonObject.getString("code").equals("0") && jsonObject.getString("message").equals("成功")) {
                    switch (jsonObject.getJSONObject("result").getString("res")) {
                        case "1": {
                            flag = userService.updateByPhone(phone, name, idCard);
                            if (!flag) {
                                System.out.println("实名成功，更新数据库失败");
                            }
                        }
                            break;
                        case "2":
                            System.out.println("内容不一致");
                            break;
                        case "3":
                            System.out.println("查无此人");
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("调用接口失败，请找到问题重试" + e.getMessage());
        }
        return flag;
    }
}
