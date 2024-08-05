package com.jnd.strategy.impl;

import com.jnd.constant.AuthConstants;
import com.jnd.strategy.LoginStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @Classname MemberStrategy
 * @Description 商城用户系统登录具体实现策略
 * @Version 1.0.0
 * @Date 2024/8/5 12:09
 * @Created by jnd
 */
@Service(AuthConstants.MEMBER_LOGIN)
public class MemberStrategy implements LoginStrategy {
    @Override
    public UserDetails realLogin(String username) {
        return null;
    }
}
