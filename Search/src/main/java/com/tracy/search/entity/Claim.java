package com.tracy.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(indexName = "claim",createIndex = false)
@AllArgsConstructor
@NoArgsConstructor
public class Claim implements Serializable {
    Integer id_auto;//自增主键
    @Id
    @Field(name = "id",type = FieldType.Text)
    String id;
    @Field(name = "tree",type = FieldType.Text)
    String tree;

    @Override
    public String toString() {
        return "id:"+getId()+",tree:"+tree;
    }

}
