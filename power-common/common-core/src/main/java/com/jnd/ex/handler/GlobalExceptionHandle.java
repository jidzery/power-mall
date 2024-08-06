package com.jnd.ex.handler;

import com.jnd.constant.BusinessEnum;
import com.jnd.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * @Classname GlobalExceptionHandle
 * @Description 全局异常处理类
 * @Version 1.0.0
 * @Date 2024/8/6 9:48
 * @Created by jnd
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler(BussinessException.class)
    public Result<String> bussinessException(BussinessException e){
        log.error(e.getMessage());
        return Result.fail(BusinessEnum.OPERATION_FAIL.getCode(),e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<String> runtimeException(RuntimeException e){
        log.error(e.getMessage());
        return Result.fail(BusinessEnum.SERVER_INNER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> accessDeniedException(AccessDeniedException e){
        log.error(e.getMessage());
        try {
            throw e;
        } catch (AccessDeniedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
