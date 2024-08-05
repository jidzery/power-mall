package com.jnd.impl;

import com.jnd.constant.AuthConstants;
import com.jnd.factory.LoginStrategyFactory;
import com.jnd.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname userDetailsServiceImpl
 * @Description 项目自己的认证流程
 * @Version 1.0.0
 * @Date 2024/8/5 10:57
 * @Created by jnd
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginStrategyFactory loginStrategyFactory;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //从请求头中获取登录类型
        String loginType = request.getHeader(AuthConstants.LOGIN_TYPE);

        if(!StringUtils.hasText(loginType)){
            throw new InternalAuthenticationServiceException("非法登录，登录类型不匹配");
        }

        //通过登录策略工厂获取具体的登录策略对象
        LoginStrategy instance = loginStrategyFactory.getInstance(loginType);
        return instance.realLogin(username);

    }
}
