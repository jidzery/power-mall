package com.jnd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname WhiteUrlsConfig
 * @Description 请求白名单配置类
 * @Version 1.0.0
 * @Date 2024/8/4 10:28
 * @Created by jnd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "gateway.white")
@RefreshScope
public class WhiteUrlsConfig {

    /**
     * 放行的路径集合
     */
    private List<String> allowUrls;
}
