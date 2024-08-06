package com.jnd.ex.handler;

/**
 * @Classname BussinessException
 * @Description 自定义业务异常
 * @Version 1.0.0
 * @Date 2024/8/6 9:57
 * @Created by jnd
 */
public class BussinessException extends RuntimeException{
    public BussinessException(String message) {
        super(message);
    }
}
