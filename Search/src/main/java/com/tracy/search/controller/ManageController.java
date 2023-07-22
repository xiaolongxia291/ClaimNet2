package com.tracy.search.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tracy.search.entity.Text;
import com.tracy.search.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    ManageService manageService;

    //插入单条数据
    @PostMapping("/insert")
    public Boolean insert(@RequestBody Text text){
        return manageService.save(text);
    }

    //单条删除、批量删除
    @PostMapping("/delete")
    public Boolean delete(@RequestBody List<Integer> ids){
        boolean res=true;
        for(Integer id:ids){
            if(!manageService.removeById(id)){
                res=false;
            }
        }
        return res;
    }

    //更新
    @PostMapping("/update")
    public Boolean update(@RequestBody Text text){
        return manageService.updateById(text);
    }

    //条件查询
    @PostMapping("/query")
    public Page<Text> query(@RequestBody Text text,Integer pageNum, Integer pageSize){
        Page<Text> userPage = new Page<>();
        userPage.setCurrent(pageNum);
        userPage.setSize(pageSize);
        return manageService.query(userPage,text);
    }
}
