package com.tracy.search.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tracy.search.entity.Text;
import com.tracy.search.entity.User;
import com.tracy.search.service.ManageService;
import com.tracy.search.service.UserService;
import com.tracy.search.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    ManageService manageService;
    @Autowired
    UserService userService;

    //插入单条数据
    @PostMapping("/insert")
    public Boolean insert(HttpServletRequest request,@RequestBody Text text){
        return manageService.save(text);
    }

    //单条删除、批量删除
    @PostMapping("/delete")
    public Boolean delete(HttpServletRequest request,@RequestBody List<Integer> ids){
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
    public Boolean update(HttpServletRequest request,@RequestBody Text text){
        return manageService.updateById(text);
    }

    //条件查询
    @PostMapping("/query")
    public Page<Text> query(HttpServletRequest request,@RequestBody Text text,Integer pageNum, Integer pageSize){
        Page<Text> userPage = new Page<>();
        userPage.setCurrent(pageNum);
        userPage.setSize(pageSize);
        return manageService.query(userPage,text);
    }

    @PostMapping("/AcceptRegister")
    public Result<User> acc_register(HttpServletRequest request,@RequestBody User user){
        return userService.acc_register(user);
    }

}
