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

    /**
     * 登录URL
     */
    String LOGIN_URL = "/doLogin";

    /**
     * 登出URL
     */
    String LOGOUT_URL = "/doLogout";

    /**
     * 登录类型
     */
    String LOGIN_TYPE = "loginType";

    /**
     * 登录类型值：商城后台管理用户登录
     */
    String SYS_USER_LOGIN = "sysUserLogin";

    /**
     * 登录类型值：商城购物系统用户登录
     */
    String MEMBER_LOGIN = "memberLogin";

    /**
     * token有效时长
     */
    Long TOKEN_TIME = 14400L;

    /**
     * TOKEN阈值：3600 秒
     */
    Long TOKEN_EXPIRE_THRESHOLD_TIME = 60*60L;
}


