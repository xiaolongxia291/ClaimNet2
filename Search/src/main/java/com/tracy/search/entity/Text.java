package com.tracy.search.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Document(indexName = "text",createIndex = false)
public class Text implements Serializable {
    Integer id_auto;//用于mysql的自增主键，便于增删改查管理
    @Id
    @Field(name = "id",type = FieldType.Text)
    String id;
    @Field(name = "application_no",type = FieldType.Text)
    String applicationNo;
    @Field(name = "content",type = FieldType.Text)
    String content;
    @Field(name = "date",type = FieldType.Text)
    String date;
    @Field(name = "entity",type = FieldType.Text)
    String entity;
    @Field(name = "feature",type = FieldType.Text)
    String feature;

    @Override
    public String toString() {
        return "id:"+getId()+",application_no:"+applicationNo+",content:"+content+",date:"+date
                +",entity:"+entity+",feature:"+feature;
    }
}
