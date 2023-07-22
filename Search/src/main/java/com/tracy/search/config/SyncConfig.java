package com.tracy.search.config;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class SyncConfig {
    @Value("${aync.mysqlHost}")
    private String mysqlHostname;
    @Value("${aync.mysqlPort}")
    private Integer mysqlPort;
    @Value("${spring.datasource.username}")
    private String mysqlUsername;
    @Value("${spring.datasource.password}")
    private String mysqlPassword;

    @Bean
    public BinaryLogClient binaryLogClient(){
        BinaryLogClient binaryLogClient = new BinaryLogClient(mysqlHostname, mysqlPort, mysqlUsername, mysqlPassword);
        //MySQL二进制日志（binlog）客户端的保活时间间隔
        binaryLogClient.setKeepAliveInterval(TimeUnit.SECONDS.toMillis(1));
        //是否开启MySQL二进制日志（binlog）客户端的保活机制
        binaryLogClient.setKeepAlive(true);
        //用来反序列化MySQL binlog事件
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        binaryLogClient.setEventDeserializer(eventDeserializer);
        return binaryLogClient;
    }
}
