package com.jnd.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname SysUser
 * @Description TODO
 * @Version 1.0.0
 * @Date 2024/8/5 14:34
 * @Created by jnd
 */
/**
 * 系统用户
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class LoginSysUser implements Serializable {
    @TableId(value = "user_id", type = IdType.INPUT)
    private Long userId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 状态  0：禁用   1：正常
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 用户所在的商城Id
     */
    @TableField(value = "shop_id")
    private Long shopId;

    private static final long serialVersionUID = 1L;
}