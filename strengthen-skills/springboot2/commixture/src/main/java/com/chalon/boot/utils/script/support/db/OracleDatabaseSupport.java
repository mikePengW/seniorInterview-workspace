package com.chalon.boot.utils.script.support.db;

import com.chalon.boot.utils.script.support.DatabaseSupport;

import static org.apache.commons.lang3.StringUtils.equalsAnyIgnoreCase;

public class OracleDatabaseSupport implements DatabaseSupport {

    @Override
    public String getSelectOne(String tableName) {
        return "select * from " + tableName + " where rownum = 1";
    }

    @Override
    public String toHiveColumn(String columnName, String columnType) {
        if (equalsAnyIgnoreCase(columnType, "CHAR", "NCHAR", "NVARCHAR2", "VARCHAR2", "VARCHAR")) {
            return columnName + " " + "STRING";
        }
        if (equalsAnyIgnoreCase(columnType, "YEAR", "MONTH", "DAY", "HOUR", "MINUTE")) {
            return columnName + " " + "INT";
        }
        if (equalsAnyIgnoreCase(columnType, "NUMBER", "INTEGER")) {
            return columnName + " " + "BIGINT";
        }
        if (equalsAnyIgnoreCase(columnType, "DATE", "TIMESTAMP", "INTERVAL YEAR", "INTERVAL DAY", "SECOND", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TIMEZONE_REGION", "TIMEZONE_ABBR", "TIMESTAMP WITH TIME ZONE", "TIMESTAMP WITH LOCAL TIME ZONE")) {
            return columnName + " " + "STRING";
        }
        if (equalsAnyIgnoreCase(columnType, "FLOAT", "BINARY_FLOAT")) {
            return columnName + " " + "FLOAT";
        }
        if (equalsAnyIgnoreCase(columnType, "BINARY_DOUBLE")) {
            return columnName + " " + "DOUBLE";
        }
        if (equalsAnyIgnoreCase(columnType, "BLOB", "CLOB", "NCLOB", "BFILE")) {
            return columnName + " " + "BINARY";
        }
        if (equalsAnyIgnoreCase(columnType, "LONG", "RAW", "LONG RAW", "ROWID", "UROWID")) {
            return columnName + " " + "STRING";
        }
        throw new RuntimeException("未知的字段类型: " + columnType);
    }

    @Override
    public String toSqoopColumn(String columnName, String columnType) {
        if (equalsAnyIgnoreCase(columnType, "DATE", "TIMESTAMP")) {
            String s = "TO_CHAR(" + columnName + ",'yyyy-mm-dd hh24:mi:ss') as " + columnName;
            return s;
        } else {
            return columnName;
        }
    }

    @Override
    public boolean isNumberType(String type) {
        if (equalsAnyIgnoreCase(type, "YEAR", "MONTH", "DAY", "HOUR", "MINUTE", "NUMBER", "INTEGER")) {
            return true;
        }
        if (equalsAnyIgnoreCase(type, "FLOAT", "BINARY_FLOAT", "BINARY_DOUBLE")) {
            return true;
        }
        return false;
    }

}
