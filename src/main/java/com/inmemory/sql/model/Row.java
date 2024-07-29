package com.inmemory.sql.model;

import java.util.HashMap;
import java.util.Map;

public class Row {
    private final Map<String, Object> data;

    public Row() {
        this.data = new HashMap<>();
    }

    public void setValue(String columnName, Object value) {
        data.put(columnName, value);
    }

    public Object getValue(String columnName) {
        return data.get(columnName);
    }

    public Map<String, Object> getData() {
        return new HashMap<>(data);
    }
}
