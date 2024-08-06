package com.jnd.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Classname SecurityUser
 * @Description Security安全框架认识的安全用户对象
 * @Version 1.0.0
 * @Date 2024/8/5 14:40
 * @Created by jnd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUser implements UserDetails {

    // 商城后台管理系统用户相关属性
    private Long userId;
    private String username;
    private String password;
    private Integer status;
    private Long shopId;
    private String loginUserType;
    private Set<String> perms = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.loginUserType+this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status == 1;
    }

    @Override
    public boolean isEnabled() {
        return this.status == 1;
    }

    public Set<String> getPerms() {
        HashSet<String> finalPerms = new HashSet<>();
        // 循环遍历用户权限集合
        perms.forEach(perm ->{
            // 判断是否包含，
            if(perm.contains(",")){
                // 包含说明一条权限里有多个权限，需要进行分割
                String[] realPerms = perm.split(",");
                for(String realPerm : realPerms){
                    finalPerms.add(realPerm);
                }
            }
            else {
                // 不包含，即一条权限
                finalPerms.add(perm);
            }
        });
        return perms;
    }
}
