package com.lengch.common;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author : Lengchenghao
 * @create : 2023-11-16-20:36
 */

@Getter
@AllArgsConstructor
public class Result<T> {

    private String code;
    private String msg;
    private T data;

    public static <T> Result<T> success(){
        return new Result<>(Constants.Code_Success,"成功",null);
    }

    public static <T> Result<T> success(T data){
        return new Result<>(Constants.Code_Success,"成功",data);
    }

    public static <T> Result<T> success(String msg,T data){
        return new Result<>(Constants.Code_Success,msg,data);
    }

    public static <T> Result<T> success(String code,String msg,T data){
        return new Result<>(code,msg,data);
    }

    public static <T> Result<T> fail(String code,String msg){
        return new Result<>(code,msg,null);
    }

    public static <T> Result<T> fail(String msg){
        return new Result<>(Constants.Code_Fail,msg,null);
    }

    public static <T> Result<T> fail(){
        return new Result<>(Constants.Code_Fail,"失败",null);
    }

    public static <T> Result<T> error(String code,String msg){
        return new Result<>(code,msg,null);
    }

    public static <T> Result<T> error(String msg){
        return new Result<>(Constants.Code_Fail,msg,null);
    }

    public static <T> Result<T> error(){
        return new Result<>(Constants.Code_Fail,"错误",null);
    }
}
