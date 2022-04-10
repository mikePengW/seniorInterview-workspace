package com.chalon.boot.utils.script.support;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class TableMeta {

    private String tableName;

    private List<String> primaryKey;

    private List<Pair<String, String>> columns;

    public String getColumnType(String columnName) {
        for (Pair<String, String> c : columns) {
            if (c.getLeft().equals(columnName)) {
                return c.getRight();
            }
        }
        return null;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(List<String> primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<Pair<String, String>> getColumns() {
        return columns;
    }

    public void setColumns(List<Pair<String, String>> columns) {
        this.columns = columns;
    }

}
