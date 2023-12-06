package com.lengch.config;

import java.lang.annotation.*;

/**
 * 免认证注解
 * @author : Lengchenghao
 * @create : 2023-12-06-11:00
 */
@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface NoAuth {
}
