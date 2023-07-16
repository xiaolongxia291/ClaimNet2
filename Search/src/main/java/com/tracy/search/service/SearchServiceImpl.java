package com.tracy.search.service;

import com.tracy.search.entity.Claim;
import com.tracy.search.entity.Text;
import com.tracy.search.repository.ClaimRepository;
import com.tracy.search.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{
    @Autowired
    ClaimRepository claimRepository;
    TextRepository textRepository;

    @Override
    public List<Text> searchByKeyword(String keyword, int curPage, int pageSize) {
        return textRepository.findTextsByContent(keyword, PageRequest.of(curPage - 1, pageSize));
    }

    @Override
    public List<Text> searchByAppno(String appno, int curPage, int pageSize) {
        return textRepository.findTextByApplicationNo(appno,PageRequest.of(curPage - 1, pageSize));
    }

    @Override
    public Claim buildTree(String id) {
        return claimRepository.findClaimById(id);
    }
}
