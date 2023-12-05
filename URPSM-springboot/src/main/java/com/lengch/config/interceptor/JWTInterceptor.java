package com.lengch.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lengch.common.Constants;
import com.lengch.entity.User;
import com.lengch.exception.ServiceException;
import com.lengch.service.IUserService;
import com.lengch.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Lengchenghao
 * @create : 2023-11-20-21:45
 */

public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        //判断是否映射到方法，如果不是映射到方法直接放行
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.Code_Token,"请登录");
        }

        String userId;
        try {

            userId= JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException ex) {
            throw new ServiceException(Constants.Code_Token,"token解析失败");

        }
        //通过token中的userid查询用户是否存在
        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(Constants.Code_Token,"用户不存在，请重新登录");
        }
        //通过密码加签进行验证token是否合法
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {

            jwtVerifier.verify(token);

        } catch (JWTVerificationException je) {
            throw new ServiceException(Constants.Code_Token,"token验证失败");
        }


        return true;
    }
}
