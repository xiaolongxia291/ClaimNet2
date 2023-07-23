package com.tracy.search.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.tracy.search.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MysqlElasticsearchSync {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    BinaryLogClient binaryLogClient;
    @Autowired
    SyncService syncService;
    @Value("${sync.base}")
    String basename;
    @Value("${sync.table}")
    String tablename;
    private static final Map<Long,String> tableMap=new HashMap<>();


    public void start() throws IOException {
        String table_name=basename+"."+tablename;
        // 注册MySQL binlog事件监听器
        binaryLogClient.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof TableMapEventData) {
                // 如果是TABLE_MAP事件，获取对应的表的元数据信息
                TableMapEventData tableMapEventData = (TableMapEventData) data;
                long tableId = tableMapEventData.getTableId();
                String tableName = tableMapEventData.getDatabase() + "." + tableMapEventData.getTable();
                tableMap.put(tableId, tableName);
            }
            else if (data instanceof WriteRowsEventData) {
                // 处理插入事件
                WriteRowsEventData eventData = (WriteRowsEventData) data;
                if(table_name.equals(tableMap.get(eventData.getTableId()))){
                    try {
                        syncService.handleInsert(eventData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (data instanceof DeleteRowsEventData) {
                // 处理删除事件
                DeleteRowsEventData eventData = (DeleteRowsEventData) data;
                if(table_name.equals(tableMap.get(eventData.getTableId()))){
                    try {
                        syncService.handleDelete( eventData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (data instanceof UpdateRowsEventData) {
                // 处理更新事件
                UpdateRowsEventData eventData = (UpdateRowsEventData) data;
                if(table_name.equals(tableMap.get(eventData.getTableId()))) {
                    try {
                        syncService.handleUpdate(eventData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        binaryLogClient.connect();
    }


}