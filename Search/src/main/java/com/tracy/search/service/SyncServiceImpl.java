package com.tracy.search.service;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.tracy.search.entity.Text;
import com.tracy.search.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service
public class SyncServiceImpl implements SyncService {
    @Autowired
    TextRepository textRepository;

    @Override
    public void handleInsert(WriteRowsEventData eventData) {
        Serializable[] row = eventData.getRows().get(0);
        Text text=new Text((Long)row[0],(String)row[1],(String)row[2],(String)row[3],(String)row[4],(String)row[5],(String)row[6]);
        textRepository.save(text);
    }

    @Override
    public void handleDelete(DeleteRowsEventData eventData) {
        Serializable[] row = eventData.getRows().get(0);
        Text text=new Text((Long)row[0],(String)row[1],(String)row[2],(String)row[3],(String)row[4],(String)row[5],(String)row[6]);
        textRepository.delete(text);
    }

    @Override
    public void handleUpdate(UpdateRowsEventData eventData) {
        Map.Entry<Serializable[],Serializable[]> row_entry = eventData.getRows().get(0);
        Text text=new Text((Long)row_entry.getKey()[0],(String)row_entry.getValue()[1],(String)row_entry.getValue()[2],(String)row_entry.getValue()[3],(String)row_entry.getValue()[4],(String)row_entry.getValue()[5],(String)row_entry.getValue()[6]);
        textRepository.save(text);
    }
}
