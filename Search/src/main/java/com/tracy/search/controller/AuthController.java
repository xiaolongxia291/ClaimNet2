package com.tracy.search.controller;

import com.tracy.search.entity.User;
import com.tracy.search.service.UserService;
import com.tracy.search.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user){
        return userService.register(user);
    }
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user){
        return userService.login(user);
    }
}
