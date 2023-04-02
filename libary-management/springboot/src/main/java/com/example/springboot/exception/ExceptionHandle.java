package com.example.springboot.exception;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理
@Slf4j
@RestControllerAdvice
public class ExceptionHandle {
//处理业务层报的错误
    @ExceptionHandler(value =ServiceException.class)
    public Result ServiceExceptionError(ServiceException e){
//        如果出现错误，将会把错误的信息给拦截掉
        log.error("业务异常",e);
        String code = e.getCode();
        if(StrUtil.isNotBlank(code)){
            return Result.error(code,e.getMessage());
        }
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(value =Exception.class)
    public Result exceptionHandler(Exception e){
//        如果出现错误，将会把错误的信息给拦截掉
        log.error("系统错误",e);
        return Result.error("系统错误");
    }
}
