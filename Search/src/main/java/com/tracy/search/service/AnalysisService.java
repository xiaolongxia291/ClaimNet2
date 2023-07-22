package com.tracy.search.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface AnalysisService {

    List<String> hot(String type);

    HashMap<Date, Integer> v(List<Date> dates,String type);
}
