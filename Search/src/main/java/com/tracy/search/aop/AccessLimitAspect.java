package com.tracy.search.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class AccessLimitAspect {
    @Resource
    RedisTemplate<String,Integer> redisTemplate;
    @Value("${redis.time}")
    int time;
    @Value("${redis.access}")
    int access;

    @Before("execution(* com.tracy.search.controller.SearchController.*.*(..))")
    public void checkLimit(JoinPoint joinPoint) {
        String signature=joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName()+"()";
        if(!accessLimit(signature)){
            throw new SecurityException("达到了限流上限！");
        }
    }

    public boolean accessLimit(String url){
        //1 如果是首次访问
        redisTemplate.opsForValue().setIfAbsent(url,0,time, TimeUnit.SECONDS);
        //2 +1
        redisTemplate.opsForValue().increment(url,1);
        //3 判断是否达到次数限制
        if(redisTemplate.opsForValue().get(url)>access){
            return false;
        }else{
            return true;
        }
    }
}