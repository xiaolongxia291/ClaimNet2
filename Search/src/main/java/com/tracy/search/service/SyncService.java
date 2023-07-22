package com.tracy.search.service;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

public interface SyncService {
    void handleInsert(WriteRowsEventData eventData);

    void handleDelete(DeleteRowsEventData eventData);

    void handleUpdate(UpdateRowsEventData eventData);
}
