package com.inmemory.sql.model.index;

import com.inmemory.sql.model.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondaryIndex implements Index {

    private final Map<Object, List<Row>> index;
    public SecondaryIndex() {
        this.index = new HashMap<>();
    }
    @Override
    public void add(Object key, Row row) {
        index.computeIfAbsent(key, k -> new ArrayList<>()).add(row);
    }

    @Override
    public void remove(Object key) {
        index.remove(key);
    }

    @Override
    public List<Row> get(Object key) {
        return index.getOrDefault(key, new ArrayList<>());
    }
}
