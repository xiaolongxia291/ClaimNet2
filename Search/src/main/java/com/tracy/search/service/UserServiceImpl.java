package com.tracy.search.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracy.search.entity.User;
import com.tracy.search.repository.UserDao;
import com.tracy.search.util.Encode;
import com.tracy.search.util.Result;
import com.tracy.search.util.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Value("${auth.token_alive}")
    Integer token_alive;

    @Override
    public Result<User> register(User user) {
        if(userDao.selectOne(new QueryWrapper<User>().ge("username",user.getUsername()))!=null){
            return new Result<>(1,"用户名已存在",null);
        }
        user.setPassword(Encode.en(user.getPassword()));
        userDao.insert(user);
        return new Result<>(0,"",null);
    }

    @Override
    public Result<User> acc_register(User user) {
        user.setAccept(true);
        userDao.updateById(user);
        return new Result<>(0,"",null);
    }

    @Override
    public Result<User> login(User user) {
        //判断是否存在该用户、密码是否正确、是否是已注册用户
        user.setPassword(Encode.en(user.getPassword()));
        if(userDao.selectOne(new QueryWrapper<User>()
                .ge("username",user.getUsername())
                .ge("password",user.getPassword())
                .ge("accept",1))!=null){
            //生成token，redis缓存token，并返回
            user.setToken(JWT.generate(user.getUsername()));
            List<User> data=new ArrayList<>();
            data.add(user);
            redisTemplate.opsForValue().set(user.getUsername(),user.getToken());
            redisTemplate.expire(user.getUsername(), token_alive, TimeUnit.SECONDS);
            return new Result<>(0, "", data);
        }else{
            return new Result<>(1,"用户不存在或密码错误",null);
        }
    }
}
