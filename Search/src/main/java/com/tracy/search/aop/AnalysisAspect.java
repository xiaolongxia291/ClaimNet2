package com.tracy.search.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;



@Aspect
@Component
public class AnalysisAspect {
    @Resource
    RedisTemplate<String,Object> redisTemplate;
    @Value("${redis.hotUser}")
    int userCount;
    @Value("${redis.hotKeyword}")
    int keywordCount;

    //home方法每被调用一次，就更新一次uv和vv
    @Before("execution(* com.tracy.search.controller.SearchController.home(..))")
    public void updateIndex1(JoinPoint joinPoint) {
        HttpServletRequest request=(HttpServletRequest)joinPoint.getArgs()[0];
        String user_ip=request.getRemoteAddr();
        uv_vv_update(user_ip);
    }
    //SearchController中的查询方法每被调用一次，热点词和热门用户就更新一次
    @Before("execution(* com.tracy.search.controller.SearchController.*(..))")
    public void updateIndex2(JoinPoint joinPoint) {
        HttpServletRequest request=(HttpServletRequest)joinPoint.getArgs()[0];
        String user_ip=request.getRemoteAddr();
        String word=(String)joinPoint.getArgs()[1];
        hot_keyword_user_update(word,"HotKeyword");
        hot_keyword_user_update(user_ip,"HotUser");
    }

    //热点搜索词、热门用户统计
    public void hot_keyword_user_update(String word,String type){
        redisTemplate.opsForZSet().addIfAbsent(type,word,0);
        redisTemplate.opsForZSet().incrementScore(type,word,1);
        if(type.equals("HotUser")){
            if(redisTemplate.opsForZSet().size(type)>userCount){
                redisTemplate.opsForZSet().popMin("HotUser");
            }
        }else{
            if(redisTemplate.opsForZSet().size(type)>keywordCount){
                redisTemplate.opsForZSet().popMin("HotKeyword");
            }
        }

    }
    //访问次数、访问人数统计
    public void uv_vv_update(String user_ip){
        Date today=new Date();
        long tomorrow_timestamp=today.getTime()+today.getTime() + (24 * 60 * 60 * 1000);
        //今天的访问次数+1
        redisTemplate.opsForHash().increment("VV",today,1);
        //今天的访问人数更新，如果是今天的新用户则今日的UV+1
        String today_uv_key=today + "UV";
        if(Boolean.FALSE.equals(redisTemplate.hasKey(today_uv_key))){
            redisTemplate.opsForHash().increment("UV",today,1);
            redisTemplate.opsForSet().add(today_uv_key,user_ip);
            redisTemplate.expireAt(today_uv_key,new Date(tomorrow_timestamp));//明天过期
        }
        else{
            if(Boolean.FALSE.equals(redisTemplate.opsForSet().isMember(today_uv_key, user_ip))){
                redisTemplate.opsForHash().increment("UV",today,1);
            }
            redisTemplate.opsForSet().add(today_uv_key,user_ip);
        }
    }
}
