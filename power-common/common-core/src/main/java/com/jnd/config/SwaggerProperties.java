package com.jnd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname SwaggerProperties
 * @Description Swagger配置属性对象
 * @Version 1.0.0
 * @Date 2024/8/5 22:05
 * @Created by jnd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "swagger3")
public class SwaggerProperties {

    /**
     * 描述生成文档的包名
     */
    private String basePackage;

    /**
     * 作者名字
     */
    private String name;

    /**
     * 主页url
     */
    private String url;

    /**
     *邮箱
     */
    private String email;

    /**
     *标题
     */
    private String title;

    /**
     *描述
     */
    private String description;

    /**
     *服务信息
     */
    private String license;

    /**
     *url地址
     */
    private String licenseUrl;

    /**
     *服务团队
     */
    private String termsOfServiceUrl;

    /**
     * 版本号
     */
    private String version;
}
