package com.tracy.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalysisServiceImpl implements AnalysisService{
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Value("${redis.hotUser}")
    int userCount;
    @Value("${redis.hotKeyword}")
    int keywordCount;

    @Override
    public List<String> hot(String type) {
        List<String> res=new ArrayList<>();
        Set<Object> zsets=redisTemplate.opsForZSet().range(type,0,userCount-1);
        for(Object obj:zsets){
            res.add((String)obj);
        }
        return res;
    }

    @Override
    public HashMap<Date, Integer> v(List<Date> dates,String type) {
        HashMap<Date,Integer> res=new HashMap<>();
        for(Date date:dates){
            res.put(date,(Integer)redisTemplate.opsForHash().get(type,date));
        }
        return res;
    }
}
