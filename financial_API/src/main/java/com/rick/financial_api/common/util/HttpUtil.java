package com.rick.financial_api.common.util;


import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

import java.util.Map;


public class HttpUtil {

    /**
     * 发送 HTTP GET 请求
     *"Authorization", "APPCODE " + appcode
     */
    public static CloseableHttpResponse sendGet(@NotNull String url, @Nullable String appCode) throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        // 设置请求头
        if (appCode != null) {//需要一个空格
            httpGet.addHeader("Authorization", "APPCODE "+appCode);
        }

        // 发送请求并获取响应
        CloseableHttpResponse response = httpClient.execute(httpGet);
        System.out.println("返回状态码" + response.getStatusLine().getStatusCode() +", message:"+response.getStatusLine().getReasonPhrase());
        HttpEntity responseEntity = response.getEntity();
        String result = EntityUtils.toString(responseEntity, "UTF-8");
        System.out.println("响应体内容：" + result);

        return response;
    }

    /**
     * 发送 HTTP POST 请求
     */
    public static CloseableHttpResponse sendPost(@NotNull String url, @Nullable String appCode, @Nullable Map<String, String> body) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        if (appCode != null) {//需要一个空格
            httpPost.addHeader("Authorization", "APPCODE "+appCode);
//            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
        }


//        // url方式
//        StringBuilder requestBody = new StringBuilder();
//        for (Map.Entry<String, String> entry : body.entrySet()) {
//            if (requestBody.length() > 0) {
//                requestBody.append("&");
//            }
//            requestBody.append(entry.getKey()).append("=").append(entry.getValue());
//        }
//        StringEntity entity = new StringEntity(requestBody.toString(), "UTF-8");
//        httpPost.setEntity(entity);

        // 设置请求体
        if (body != null) {

            String requestBody = JSON.toJSONString(body);
            HttpEntity entity = new StringEntity(requestBody, "UTF-8"/*ContentType.APPLICATION_JSON*/);

            // 第二种方式：将请求参数放在请求体中
//            List<NameValuePair> formParams = new ArrayList<>();//存储请求参数键值对
//            for (Map.Entry<String, String> entry : body.entrySet()) {
//                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//            HttpEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);//请求体部分
//            System.out.println(entity);
            System.out.println("请求体内容：" + requestBody);
            httpPost.setEntity(entity);

        }

        // 发送请求并获取响应
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println("返回状态码" + response.getStatusLine().getStatusCode() +", message:"+response.getStatusLine().getReasonPhrase());
        HttpEntity responseEntity = response.getEntity();
        String result = EntityUtils.toString(responseEntity, "UTF-8");
        System.out.println("响应体内容：" + result);

        return response;
    }




}
