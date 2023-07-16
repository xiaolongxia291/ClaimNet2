package com.tracy.search.controller;

import com.tracy.search.entity.Claim;
import com.tracy.search.entity.Text;
import com.tracy.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;

    @PostMapping("/searchByKeyword")
    public List<Text> searchByKeyword(@RequestParam String keyword,@RequestParam int curPage,@RequestParam int pageSize){
        return searchService.searchByKeyword(keyword,curPage,pageSize);
    }

    @PostMapping("/searchByAppno")
    public List<Text> searchByAppno(@RequestParam String appno, @RequestParam int curPage, @RequestParam int pageSize){
        return searchService.searchByAppno(appno,curPage,pageSize);
    }

    @PostMapping("/buildTree")
    public Claim buildTree(@RequestParam String id){
        return searchService.buildTree(id);
    }
}
