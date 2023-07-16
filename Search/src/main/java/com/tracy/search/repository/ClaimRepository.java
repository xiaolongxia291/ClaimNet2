package com.tracy.search.repository;

import com.tracy.search.entity.Claim;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ClaimRepository  extends ElasticsearchRepository<Claim,String> {
    Claim findClaimById(String id);
}
