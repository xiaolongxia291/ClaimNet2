package com.tracy.search;

import com.tracy.search.sync.MysqlElasticsearchSync;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@EnableTransactionManagement
@SpringBootApplication
public class SearchApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SearchApplication.class, args);
        new MysqlElasticsearchSync().start();
    }

}
