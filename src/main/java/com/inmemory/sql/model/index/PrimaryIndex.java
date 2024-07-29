package com.inmemory.sql.model.index;

import com.inmemory.sql.model.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimaryIndex implements Index {
    private final Map<Object, Row> index;

    public PrimaryIndex() {
        this.index = new HashMap<>();
    }
    @Override
    public void add(Object key, Row row) {
        if(index.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate Primary Key: "+ key);
        }
        index.put(key, row);
    }

    @Override
    public void remove(Object key) {
        index.remove(key);
    }

    @Override
    public List<Row> get(Object key) {
        Row row = index.get(key);
        return row!=null ? List.of(row) : new ArrayList<>();
    }
}
