package com.inmemory.sql.model;

import com.inmemory.sql.model.index.PrimaryIndex;
import com.inmemory.sql.model.index.SecondaryIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
    private String name;
    private List<Column> columns;
    private PrimaryIndex primaryIndex;
    private Map<String, SecondaryIndex> secondaryIndexes;
    private List<Row> rows;
    private String primaryKeyColumn;

    public Table(String name, List<Column> columns, String primaryKeyColumn) {
        this.name = name;
        this.columns = columns;
        this.primaryIndex = new PrimaryIndex();
        this.secondaryIndexes = new HashMap<>();
        this.rows = new ArrayList<>();
        //Ensure primary key column exists;
        if(columns.stream().noneMatch(col -> col.getName().equals(primaryKeyColumn))) {
            throw new IllegalArgumentException("Primary key column does not exist");
        }
        this.primaryKeyColumn = primaryKeyColumn;
    }

    public void addRow(Row row) {
        Object primaryKey = row.getValue(this.primaryKeyColumn);
        primaryIndex.add(primaryKey, row);
        for(Map.Entry<String, SecondaryIndex> entry: secondaryIndexes.entrySet()) {
            Object secondaryKey = row.getValue(entry.getKey());
            entry.getValue().add(secondaryKey, row);
        }
        rows.add(row);
    }

    public void removeRow(Object primaryKey) {
        List<Row> rowsToRemove = primaryIndex.get(primaryKey);
        for(Row row: rowsToRemove) {
            for(Map.Entry<String, SecondaryIndex> entry: secondaryIndexes.entrySet()) {
                Object secondaryKey = row.getValue(entry.getKey());
                entry.getValue().remove(secondaryKey);
            }
            rows.remove(row);
        }
        primaryIndex.remove(primaryKey);
    }

    public List<Row> getRows() {
        return new ArrayList<>(rows);
    }

    public void addSecondaryIndex(String columnName) {
        if(secondaryIndexes.containsKey(columnName)) {
            throw new IllegalArgumentException("Secondary index already exists for column: "+columnName);
        }
        SecondaryIndex secondaryIndex = new SecondaryIndex();
        for(Row row: rows) {
            Object key = row.getValue(columnName);
            secondaryIndex.add(key, row);
        }
        secondaryIndexes.put(columnName, secondaryIndex);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public PrimaryIndex getPrimaryIndex() {
        return primaryIndex;
    }

    public void setPrimaryIndex(PrimaryIndex primaryIndex) {
        this.primaryIndex = primaryIndex;
    }

    public Map<String, SecondaryIndex> getSecondaryIndexes() {
        return secondaryIndexes;
    }

    public void setSecondaryIndexes(Map<String, SecondaryIndex> secondaryIndexes) {
        this.secondaryIndexes = secondaryIndexes;
    }

    public String getPrimaryKeyColumn() {
        return primaryKeyColumn;
    }

    public void setPrimaryKeyColumn(String primaryKeyColumn) {
        this.primaryKeyColumn = primaryKeyColumn;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
}
