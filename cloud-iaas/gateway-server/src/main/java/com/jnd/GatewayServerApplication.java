package com.jnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname GatewayServerApplication
 * @Description 网关服务模块启动类
 * @Version 1.0.0
 * @Date 2024/8/3 17:54
 * @Created by jnd
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class,args);
    }
}
