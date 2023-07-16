package com.tracy.search.service;

import com.tracy.search.entity.Claim;
import com.tracy.search.entity.Text;
import java.util.List;

public interface SearchService {


    List<Text> searchByKeyword(String keyword, int curPage, int pageSize);

    List<Text> searchByAppno(String appno, int curPage, int pageSize);

    Claim buildTree(String id);
}
