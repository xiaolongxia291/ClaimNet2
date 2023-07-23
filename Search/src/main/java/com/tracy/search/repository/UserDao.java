package com.tracy.search.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tracy.search.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {

}
