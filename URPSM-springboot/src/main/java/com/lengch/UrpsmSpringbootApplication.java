package com.lengch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching// 开启springboot-cache缓存注解
@SpringBootApplication
public class UrpsmSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrpsmSpringbootApplication.class, args);
    }

}
