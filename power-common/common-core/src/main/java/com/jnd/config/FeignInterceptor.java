package com.jnd.config;

import cn.hutool.core.util.ObjectUtil;
import com.jnd.constant.AuthConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname FeignInterceptor
 * @Description 解决服务之间调用没有token的情况
 * @Version 1.0.0
 * @Date 2024/8/6 14:37
 * @Created by jnd
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取当前请求的上下文对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        // 判断是否有值
        if(ObjectUtil.isNotNull(requestAttributes)){
             // 获取请求对象
            HttpServletRequest request = requestAttributes.getRequest();
            // 判断是否有值
            if(ObjectUtil.isNotNull(request)){
                // 获取当前请求头中的token，传递到下一个请求对象的请求头中
                String authorization = request.getHeader(AuthConstants.AUTHORIZATION);
                requestTemplate.header(AuthConstants.AUTHORIZATION,authorization);
                return;
            }
            requestTemplate.header(AuthConstants.AUTHORIZATION,AuthConstants.BEARER + "d08752d3-9a42-424b-bce5-9ebd9c162e41");
        }
    }
}
