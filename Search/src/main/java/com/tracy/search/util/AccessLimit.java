package com.tracy.search.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class AccessLimit {
    @Resource
    RedisTemplate<String,Integer> redisTemplate;
    @Value("${redis.time}")
    int time;
    @Value("${redis.access}")
    int access;

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