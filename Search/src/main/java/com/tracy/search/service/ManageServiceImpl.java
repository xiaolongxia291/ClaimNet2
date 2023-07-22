package com.tracy.search.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracy.search.entity.Text;
import com.tracy.search.repository.TextDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ManageServiceImpl extends ServiceImpl<TextDao, Text> implements ManageService{

    @Autowired
    TextDao textDao;

    @Override
    public Page<Text> query(Page<Text> textPage, Text text) {
        return textDao.selectPage(textPage,new QueryWrapper<Text>()
                .ge("application_no", text.getApplicationNo())
                .ge("date",text.getDate()));
    }
}
