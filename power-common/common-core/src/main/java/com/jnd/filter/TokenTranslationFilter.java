package com.jnd.filter;

import com.alibaba.fastjson.JSONObject;
import com.jnd.constant.AuthConstants;
import com.jnd.model.SecurityUser;
import org.aspectj.util.GenericSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Classname TokenTranslationFilter
 * @Description token转换过滤器
 * @Version 1.0.0
 * @Date 2024/8/6 12:06
 * @Created by jnd
 */
@Component
public class TokenTranslationFilter extends OncePerRequestFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * token解析过滤器：
     *      前提：
     *       只处理携带token的请求，将用户信息转换出来
     *       没有携带token的请求，交给security资源配置类中的处理器进行处理
     *
     *       1.获取token值
     *          有：
     *              token转换为用户信息
     *              将用户信息转换为security框架认识的用户消息对象
     *              再将认识的用户消息对象放到当前资源服务的容器中
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的Authorization值
        String authorization = request.getHeader(AuthConstants.AUTHORIZATION);
        // 判断是否有值
        if(StringUtils.hasText(authorization)){
            // 获取token
            String token = authorization.replaceFirst("bearer ", "");
            // 判断是否有值
            if(StringUtils.hasText(token)){
                // 解决token续签问题
                // 从redis获取token存活时长
                Long expire = stringRedisTemplate.getExpire(AuthConstants.LOGIN_TOKEN_PREFIX + token);
                // 判断是否超过系统阈值
                if(expire < AuthConstants.TOKEN_EXPIRE_THRESHOLD_TIME){
                    //给当前用户续签
                    stringRedisTemplate.expire(AuthConstants.LOGIN_TOKEN_PREFIX + token , AuthConstants.TOKEN_TIME, TimeUnit.SECONDS);
                }

                // 从redis中获取json格式字符串的用户认证信息
                String userJsonStr = stringRedisTemplate.opsForValue().get(AuthConstants.LOGIN_TOKEN_PREFIX + token);
                // 将json格式字符串的用户信息转换为SecurityUser对象
                SecurityUser securityUser = JSONObject.parseObject(userJsonStr, SecurityUser.class);

                // 处理权限
                Set<SimpleGrantedAuthority> collect = securityUser.getPerms().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

                // 创建UsernamePasswordAuthenticationToken对象
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser,null,collect);

                // 将security框架认识的用户对象存放到security上下文中
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        filterChain.doFilter(request,response);

    }
}
