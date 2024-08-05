package com.jnd.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname LoginResult
 * @Description 登录统一结果对象
 * @Version 1.0.0
 * @Date 2024/8/5 16:11
 * @Created by jnd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("登录统一结果对象")
public class LoginResult {

    @ApiModelProperty("令牌token")
    private String accessToken;

    @ApiModelProperty("有效时长")
    private Long expireIN;

}
