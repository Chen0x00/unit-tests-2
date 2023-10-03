package com.rick.financial_api.common.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 如果声明为static，存在多线程安全
 * 1.使用 synchronized 关键字，将构造方法加上同步锁
 * 2.使用 volatile 关键字，给密钥加上，避免缓存不一致
 * 3.使用线程局部变量（ThreadLocal），给密钥加上，每个线程单独一个值
 */
public class JwtUtil {

    //密匙
    private String jwtKey;

    public JwtUtil(String jwtKey) {
        this.jwtKey = jwtKey;
    }

    /**
     * 创建jwt
     */
    public  final String generateToken(Map<String,Object> data,@Nullable Integer minute){
        //设置请求头
        Map<String, Object> header = new HashMap<>();
        //固定写法
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        //设置过期时间
        Date current = new Date();
        int addMinute=120;
        if (minute != null) {
            addMinute = minute;
        }

        Date expireTime = DateUtils.addMinutes(current, addMinute);
        String token = JWT.create()
                .withHeader(header)//请求头
                .withClaim("uid",data)// 设置自定义声明,payload
                .withSubject("光明金融Token")//设置主题
                .withIssuedAt(new Date())//签名时间
                .withExpiresAt(expireTime)//过期时间
                .sign(Algorithm.HMAC256(jwtKey));// 使用 HMAC256 签名算法进行签名,验证完整性

        return token;
    }

    /**
     * 用于验证并解析 JWT 令牌的，它会对令牌进行签名验证，确保令牌未被篡改。
     * 只有在签名验证通过后，才会解析令牌并提取其中的数据。它返回一个 DecodedJWT 对象，其中包含了解析后的令牌数据。
     */
    public  boolean verifyToken(String token){

        try {
            //生成验证器
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtKey)).build();
            //生成解析器
            //令牌中通常包含了一个过期时间（exp），验证器会检查当前时间是否在过期时间之前。如果令牌已过期，验证将失败。
            /*DecodedJWT verified = */
            verifier.verify(token);
            return true;
            //验证通过后可以访问payload
            /*其它验证：检查用户的权限或角色等
             例如：JWT.require(Algorithm.HMAC256(SECRET)).withClaim("claimName", claimValue).build().verify(token) */
        } catch (JWTVerificationException e) {
            System.out.println("JWT 验证失败：" + e.getMessage());
//            e.printStackTrace();//输出异常信息
            return false;
        }

    }

    /**
     * 解码令牌，而不进行签名验证，因此不会抛出异常
     */
    public  Map<String, Claim> decodeToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaims();
    }





}
