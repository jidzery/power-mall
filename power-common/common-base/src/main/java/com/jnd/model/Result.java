package com.jnd.model;

import com.jnd.constant.BusinessEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname Result
 * @Description 项目统一响应结果对象
 * @Version 1.0.0
 * @Date 2024/8/4 12:53
 * @Created by jnd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("项目统一响应结果对象")
public class Result<T> implements Serializable {

    @ApiModelProperty("状态码")
    private Integer code = 200;

    @ApiModelProperty("消息")
    private String msg = "ok";

    @ApiModelProperty("数据")
    private T data;

    /**
     * 操作成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> success(T data){
        Result result = new Result<>();
        result.setData(data);
        return result;
    }

    /**
     * 操作失败
     * @param code
     * @param msg
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(Integer code,String msg){
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
     * 操作失败
     * @param businessEnum
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(BusinessEnum businessEnum){
        Result result = new Result<>();
        result.setCode(businessEnum.getCode());
        result.setMsg(businessEnum.getDesc());
        result.setData(null);
        return result;
    }
}
