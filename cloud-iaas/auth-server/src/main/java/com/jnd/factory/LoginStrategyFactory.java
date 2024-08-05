package com.jnd.factory;

import com.jnd.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname LoginStrategyFactory
 * @Description 登录策略工厂类
 * @Version 1.0.0
 * @Date 2024/8/5 12:13
 * @Created by jnd
 */
@Component
public class LoginStrategyFactory {

    @Autowired
    private Map<String, LoginStrategy> loginStrategyMap = new HashMap<>();

    /**
     * 根据用户登录类型获取具体的登录策略
     * @param loginType
     * @return
     */
    public LoginStrategy getInstance(String loginType){
        return loginStrategyMap.get(loginType);
    }
}
