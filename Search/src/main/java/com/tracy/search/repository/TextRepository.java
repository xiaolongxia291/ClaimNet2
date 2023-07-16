package com.tracy.search.repository;

import com.tracy.search.entity.Text;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface TextRepository extends ElasticsearchRepository<Text,String> {
    List<Text> findTextsByContent(String keyword, PageRequest pageRequest);
    List<Text> findTextByApplicationNo(String appno, PageRequest pageRequest);
}
