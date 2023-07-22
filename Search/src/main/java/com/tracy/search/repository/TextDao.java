package com.tracy.search.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tracy.search.entity.Text;
import org.springframework.stereotype.Repository;

@Repository
// 继承Mybatis-Plus提供的BaseMapper，提供基础的CRUD及分页方法
public interface TextDao extends BaseMapper<Text> {
}
