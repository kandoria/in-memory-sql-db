package com.inmemory.sql.model.index;

import com.inmemory.sql.model.Row;

import java.util.List;

public interface Index {
    void add(Object key, Row row);
    void remove(Object key);
    List<Row> get(Object key);
}
