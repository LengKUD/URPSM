package com.lengch.config;

import com.lengch.config.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : Lengchenghao
 * @create : 2023-11-20-22:33
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //swagger拦截器排除列表
        String[] swaggerExclude = new String[]{
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/**",
                "/v3/**",
                "/favicon.ico",
                "/api/**",
                //"/druid/**",
                //"/ignore/**"
                };
        registry.addInterceptor(jwtInterceptor())
                //拦截所有请求，判断token是否合法
                .addPathPatterns("/**")
                .addPathPatterns("/")
                //排除拦截页面
                .excludePathPatterns("/auth/login","/auth/regis","/**/imp","/**/exp","/files/upload","/files/download/**")
                .excludePathPatterns(swaggerExclude);
    }

    @Bean
    public JWTInterceptor jwtInterceptor(){
        return new JWTInterceptor();
    }
}
