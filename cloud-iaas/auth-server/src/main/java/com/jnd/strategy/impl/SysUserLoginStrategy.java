package com.jnd.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jnd.constant.AuthConstants;
import com.jnd.domain.LoginSysUser;
import com.jnd.mapper.LoginSysUserMapper;
import com.jnd.model.SecurityUser;
import com.jnd.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Classname SysUserLoginStrategy
 * @Description 商城后台管理系统登录具体实现策略
 * @Version 1.0.0
 * @Date 2024/8/5 12:08
 * @Created by jnd
 */
@Service(AuthConstants.SYS_USER_LOGIN)
public class SysUserLoginStrategy implements LoginStrategy {

    @Autowired
    private LoginSysUserMapper loginSysUserMapper;
    @Override
    public UserDetails realLogin(String username) {
        // 根据用户名称查询
        LoginSysUser loginSysUser = loginSysUserMapper.selectOne(new LambdaQueryWrapper<LoginSysUser>().eq(LoginSysUser::getUsername, username));

        if(ObjectUtil.isNotNull(loginSysUser)){
            // 根据用户标识查询用户权限集合
            Set<String> perms = loginSysUserMapper.selectPermsByUserId(loginSysUser.getUserId());

            //创建安全用户对象
            SecurityUser securityUser = new SecurityUser();
            securityUser.setUserId(loginSysUser.getUserId());
            securityUser.setPassword(loginSysUser.getPassword());
            securityUser.setStatus(loginSysUser.getStatus());
            securityUser.setShopId(loginSysUser.getShopId());
            securityUser.setLoginUserType(AuthConstants.SYS_USER_LOGIN);
            //判断用户权限是否有值
            if(CollUtil.isNotEmpty(perms) && perms.size() != 0){
                securityUser.setPerms(perms);
            }
            return securityUser;
        }
        return null;
    }
}
