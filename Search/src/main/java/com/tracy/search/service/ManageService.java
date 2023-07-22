package com.tracy.search.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tracy.search.entity.Text;

public interface ManageService extends IService<Text> {
    Page<Text> query(Page<Text> textPage,Text text);
}
