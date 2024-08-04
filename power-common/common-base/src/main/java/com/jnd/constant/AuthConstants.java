package com.jnd.constant;

/**
 * @Classname AuthConstants
 * @Description 认证授权常量类
 * @Version 1.0.0
 * @Date 2024/8/4 10:41
 * @Created by jnd
 */
public interface AuthConstants {

    /**
     * 请求头存放token的Key
     */
    String AUTHORIZATION = "Authorization";

    /**
     * token值前缀
     */
    String BEARER = "bearer ";

    /**
     * token值存放在redis中的前缀
     */
    String LOGIN_TOKEN_PREFIX = "login_token:";
}


