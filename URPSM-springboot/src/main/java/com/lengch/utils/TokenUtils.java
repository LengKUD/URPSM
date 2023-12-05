package com.lengch.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lengch.entity.User;
import com.lengch.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author : Lengchenghao
 * @create : 2023-11-20-18:38
 */
@Component
public class TokenUtils {


    public static IUserService userService;

    /**
     * springboot 会自动忽略static变量或者方法，如下方式给static变量注入
     * @param userService
     */
    @Autowired
    public void setUserService(IUserService userService){
        TokenUtils.userService = userService;
    }

    /**
     * 生产token
     * @param payload JWT载荷
     * @param sign JWT签名
     * @return token字符串
     */
     public static String getToken(String payload,String sign){
         return JWT.create().withAudience(payload)//载荷，可以讲userID放入
                 .withExpiresAt(DateUtil.offsetHour(new Date(),2))//设置2小时过期
                 .sign(Algorithm.HMAC256(sign));//token秘钥，可以用password放入
     }

    /**
     * 通过token中载荷部分userid获取当前用户信息
     * @return
     */
     public static User getCurrentUserByToken(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String uid = JWT.decode(token).getAudience().get(0);
                return userService.getById(uid);
            }
            return null;
        }catch (Exception e){
            return null;
        }
     }

}
