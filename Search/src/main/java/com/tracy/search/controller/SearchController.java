package com.tracy.search.controller;

import com.tracy.search.entity.Claim;
import com.tracy.search.entity.Text;
import com.tracy.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping("/home")
    public String home(HttpServletRequest request){
        return  "welcome";
    }

    @PostMapping("/searchByKeyword")
    public List<Text> searchByKeyword(HttpServletRequest request, @RequestParam String keyword, @RequestParam int curPage, @RequestParam int pageSize){
        return searchService.searchByKeyword(keyword,curPage,pageSize);
    }

    @PostMapping("/searchByAppno")
    public List<Text> searchByAppno(HttpServletRequest request,@RequestParam String appno, @RequestParam int curPage, @RequestParam int pageSize){
        return searchService.searchByAppno(appno,curPage,pageSize);
    }

    @PostMapping("/buildTree")
    public Claim buildTree(HttpServletRequest request,@RequestParam String id){
        return searchService.buildTree(id);
    }
}
