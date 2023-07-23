package com.tracy.search.repository;

import com.tracy.search.entity.Text;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextRepository extends ElasticsearchRepository<Text,String> {
    List<Text> findTextsByContent(String keyword, PageRequest pageRequest);
    List<Text> findTextByApplicationNo(String appno, PageRequest pageRequest);
}
