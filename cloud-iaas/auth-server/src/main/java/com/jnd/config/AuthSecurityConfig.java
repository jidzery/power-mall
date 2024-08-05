package com.jnd.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnd.constant.AuthConstants;
import com.jnd.constant.BusinessEnum;
import com.jnd.constant.HttpConstans;
import com.jnd.impl.UserDetailsServiceImpl;
import com.jnd.model.LoginResult;
import com.jnd.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.UUID;

/**
 * @Classname AuthSecurieyConfig
 * @Description Security安全框架配置类
 * @Version 1.0.0
 * @Date 2024/8/5 10:25
 * @Created by jnd
 */
@Configuration
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置security安全框架走自己的认证流程
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭跨站请求伪造
        http.cors().disable();
        // 关闭跨域请求
        http.csrf().disable();
        // 关闭session使用策略
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 配置登录信息
        http.formLogin()
                .loginProcessingUrl(AuthConstants.LOGIN_URL) // 设置登录URL
                .successHandler(authenticationSuccessHandler())    //设置登录成功处理器
                .failureHandler(authenticationFailureHandler());   //设置登录失败处理器


        // 配置登出信息
        http.logout()
                .logoutUrl(AuthConstants.LOGOUT_URL)    // 设置登出URL
                .logoutSuccessHandler(logoutSuccessHandler()); // 设置登出成功处理器

        // 要求所有请求都进行身份的认证
        http.authorizeHttpRequests().anyRequest().authenticated();

    }

    /**
     * 登录成功处理器
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return (request, response, authentication) -> {
            // 设置响应头信息
            response.setContentType(HttpConstans.CONTENT_TYPE);
            response.setCharacterEncoding(HttpConstans.UTF_8);

            // 使用UUID来做TOKEN
            String token = UUID.randomUUID().toString();
            // 从security框架中获取认证用户对象(SecurityUser)，并转换为JSON字符串
            String userJsonStr = JSONObject.toJSONString(authentication.getPrincipal());
            //将token当作key，用户认证对象json字符串当作value存入redis
            stringRedisTemplate.opsForValue().set(AuthConstants.LOGIN_TOKEN_PREFIX+token,userJsonStr, Duration.ofSeconds(AuthConstants.TOKEN_TIME));

            // 封装统一结果
            LoginResult loginResult = new LoginResult(token,AuthConstants.TOKEN_TIME);

            //创建一个响应结果对象
            Result<Object> result = Result.success(loginResult);

            // 返回结果
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();

        };
    }

    /**
     * 登录失败处理器
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (request, response, exception) -> {
            // 设置响应头信息
            response.setContentType(HttpConstans.CONTENT_TYPE);
            response.setCharacterEncoding(HttpConstans.UTF_8);

            //创建一个响应结果对象
            Result<Object> result = new Result<>();
            result.setCode(BusinessEnum.OPERATION_FAIL.getCode());
            if(exception instanceof BadCredentialsException){
                result.setMsg("用户名或密码有误");
            } else if (exception instanceof UsernameNotFoundException) {
                result.setMsg("用户不存在");
            } else if (exception instanceof AccountExpiredException) {
                result.setMsg("账号异常，请联系管理员");
            }else if (exception instanceof AccountStatusException) {
                result.setMsg("账号异常，请联系管理员");
            } else if (exception instanceof InternalAuthenticationServiceException) {
                result.setMsg(exception.getMessage());
            }
            else {
                result.setMsg(BusinessEnum.OPERATION_FAIL.getDesc());
            }

            // 返回结果
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    /**
     * 登出成功处理器
     * @return
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return (request, response, authentication) -> {
            // 设置响应头信息
            response.setContentType(HttpConstans.CONTENT_TYPE);
            response.setCharacterEncoding(HttpConstans.UTF_8);

            // 从请求头中获取token
            String authorization = request.getHeader(AuthConstants.AUTHORIZATION);
            String token = authorization.replaceFirst("bearer", "");

            // 将当前token从redis删除
            stringRedisTemplate.delete(AuthConstants.LOGIN_TOKEN_PREFIX+token);

            //创建一个响应结果对象
            Result<Object> result = Result.success(null);

            // 返回结果
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
