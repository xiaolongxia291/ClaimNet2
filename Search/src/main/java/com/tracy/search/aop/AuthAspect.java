package com.tracy.search.aop;


import com.tracy.search.controller.AuthController;
import com.tracy.search.util.JWT;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Component
public class AuthAspect {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    AuthController authController;

    @Before("execution(* com.tracy.search.controller.ManageController.*(..)) || " +
            "execution(* com.tracy.search.controller.AnalysisController.*(..)) ")
    public void checkToken(JoinPoint joinPoint){
        HttpServletRequest request=(HttpServletRequest)joinPoint.getArgs()[0];
        Cookie[] cookies=request.getCookies();
        //如果没有token，抛出异常
        String token=null;
        String username=null;
        for(Cookie c:cookies){
            if(c.getName().equals("token")){
                token=c.getValue();
            }
            if(c.getName().equals("username")){
                username=c.getValue();
            }
        }
        if(token==null||username==null){
            throw new SecurityException("请登录");
        }
        //获取到token，判断token是否有效，是否过期
        if(Boolean.TRUE.equals(redisTemplate.hasKey(username)) &&redisTemplate.opsForValue().get(username).equals(token)){
            return;
        }
        Claims claims = JWT.parse(token);
        if(!claims.getSubject().equals(username)||claims.getExpiration().before(new Date())){
            throw new SecurityException("请登录");
        }
    }
}
