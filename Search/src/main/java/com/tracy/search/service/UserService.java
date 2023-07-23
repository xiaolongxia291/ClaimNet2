package com.tracy.search.service;

import com.tracy.search.entity.User;
import com.tracy.search.util.Result;

public interface UserService {
    Result<User> register(User user);

    Result<User> acc_register(User user);

    Result<User> login(User user);
}
