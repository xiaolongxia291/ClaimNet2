package com.tracy.search.controller;

import com.tracy.search.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Autowired
    AnalysisService analysisService;

    @GetMapping("/hotUser")
    public List<String> hotUser(){
        return  analysisService.hot("HotUser");
    }
    @GetMapping("/hotKeyword")
    public List<String> hotKeyword(){
        return  analysisService.hot("HotKeyword");
    }
    @GetMapping("/vv")
    public HashMap<Date,Integer> vv(@RequestBody List<Date> dates){
        return  analysisService.v(dates,"VV");
    }
    @GetMapping("/uv")
    public HashMap<Date,Integer> uv(@RequestBody List<Date> dates){
        return  analysisService.v(dates,"UV");
    }
}
