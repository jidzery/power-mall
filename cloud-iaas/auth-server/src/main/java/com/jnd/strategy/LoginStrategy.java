package com.jnd.strategy;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Classname LoginStrategy
 * @Description 登录策略接口
 * @Version 1.0.0
 * @Date 2024/8/5 12:05
 * @Created by jnd
 */
public interface LoginStrategy {

    /**
     * 真正处理登录的方法
     * @param username
     * @return
     */
    UserDetails realLogin(String username);
}
