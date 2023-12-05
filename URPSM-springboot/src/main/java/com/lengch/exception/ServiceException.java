package com.lengch.exception;

import lombok.Getter;

/**
 * @author : Lengchenghao
 * @create : 2023-11-17-20:44
 * 自定义异常
 */
@Getter
public class ServiceException extends RuntimeException{
    private String code;

    public ServiceException(String code,String message) {
        super(message);
        this.code = code;
    }
}
