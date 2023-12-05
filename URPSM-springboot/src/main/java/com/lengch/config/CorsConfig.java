package com.lengch.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author : Lengchenghao
 * @create : 2023-11-07-22:04
 */
@Configuration
public class CorsConfig {

//    当前跨域最大请求时长，当前设置为一天
    private static final long Max_AGE = 24*60*60;

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");// 设置访问源地址
        corsConfiguration.addAllowedHeader("*");// 设置访问源请求头
        corsConfiguration.addAllowedMethod("*");// 设置访问源请求方法
        corsConfiguration.setMaxAge(Max_AGE);
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(source);
    }

}
