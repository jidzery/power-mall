package com.jnd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jnd.domain.LoginSysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @Classname SysUserMapper
 * @Description TODO
 * @Version 1.0.0
 * @Date 2024/8/5 14:34
 * @Created by jnd
 */
@Mapper
public interface LoginSysUserMapper extends BaseMapper<LoginSysUser> {
    /**
     * 根据用户标识查询用户权限集合
     * @param userId
     * @return
     */
    @Select("SELECT\n" +
            "    t1.perms\n" +
            "    FROM\n" +
            "    sys_menu t1\n" +
            "    JOIN sys_role_menu t2\n" +
            "    JOIN sys_user_role t3 ON ( t1.menu_id = t2.menu_id AND t2.role_id = t3.role_id)\n" +
            "    WHERE\n" +
            "    t3.user_id = #{userId}\n" +
            "    AND t1.type = 2")
    Set<String> selectPermsByUserId(Long userId);

}