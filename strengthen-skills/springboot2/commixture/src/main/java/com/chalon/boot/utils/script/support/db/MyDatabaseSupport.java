package com.chalon.boot.utils.script.support.db;

import com.chalon.boot.utils.script.support.DatabaseSupport;

import static org.apache.commons.lang3.StringUtils.equalsAnyIgnoreCase;

public class MyDatabaseSupport implements DatabaseSupport {

    @Override
    public String getSelectOne(String tableName) {
        return "select * from " + tableName + " limit 1";
    }

    @Override
    public String toHiveColumn(String columnName, String columnType) {
        if (equalsAnyIgnoreCase(columnType, "BIT", "BOOL", "BOOLEAN")) {
            return columnName + " " + "BOOLEAN";
        }
        if (equalsAnyIgnoreCase(columnType, "TINYINT", "SMALLINT", "INT", "INTEGER", "YEAR", "MEDIUMINT")) {
            return columnName + " " + "INT";
        }
        if (equalsAnyIgnoreCase(columnType, "TINYINT UNSIGNED", "SMALLINT UNSIGNED", "INT UNSIGNED", "INTEGER UNSIGNED", "MEDIUMINT UNSIGNED")) {
            return columnName + " " + "INT";
        }
        if (equalsAnyIgnoreCase(columnType, "TINYINT ZEROFILL", "SMALLINT ZEROFILL", "INT ZEROFILL", "INTEGER ZEROFILL", "MEDIUMINT ZEROFILL")) {
            return columnName + " " + "INT";
        }
        if (equalsAnyIgnoreCase(columnType, "BIGINT", "BIGINT UNSIGNED", "BIGINT ZEROFILL")) {
            return columnName + " " + "BIGINT";
        }
        if (equalsAnyIgnoreCase(columnType, "DATE", "DATETIME", "TIMESTAMP", "TIME")) {
            return columnName + " " + "STRING, " + columnName + "_i BIGINT";
        }
        if (equalsAnyIgnoreCase(columnType, "FLOAT", "FLOAT UNSIGNED", "FLOAT ZEROFILL")) {
            return columnName + " " + "FLOAT";
        }
        if (equalsAnyIgnoreCase(columnType, "DOUBLE", "DOUBLE UNSIGNED", "DOUBLE ZEROFILL", "DOUBLE PRECISION", "DOUBLE PRECISION UNSIGNED",
                "DOUBLE PRECISION ZEROFILL", "REAL", "REAL UNSIGNED", "REAL ZEROFILL")) {
            return columnName + " " + "DOUBLE";
        }
        if (equalsAnyIgnoreCase(columnType, "TINYBLOB", "BLOB", "MEDIUMBLOB", "LONGBLOB")) {
            return columnName + " " + "BINARY";
        }
        if (equalsAnyIgnoreCase(columnType, "DECIMAL", "DEC", "CHAR", "VARCHAR", "BINARY", "VARBINARY", "TINYTEXT",
                "TEXT", "MEDIUMTEXT", "LONGTEXT", "ENUM", "SET", "DECIMAL UNSIGNED", "DECIMAL ZEROFILL", "DEC UNSIGNED",
                "DEC ZEROFILL", "NUMERIC", "NUMERIC UNSIGNED", "NUMERIC ZEROFILL", "FIXED", "FIXED UNSIGNED", "FIXED ZEROFILL")) {
            return columnName + " " + "STRING";
        }
        throw new RuntimeException("未知的字段类型: " + columnType);
    }

    @Override
    public String toSqoopColumn(String columnName, String columnType) {
        if (equalsAnyIgnoreCase(columnType, "DATE", "DATETIME", "TIMESTAMP")) {
            String s = "DATE_FORMAT(" + columnName + ",'%Y-%m-%d %H:%i:%s') as " + columnName
                    + ",UNIX_TIMESTAMP(DATE_FORMAT(" + columnName + ",'%Y-%m-%d %H:%i:%s')) as " + columnName + "_i";
            return s;
        } else {
            return columnName;
        }
    }

    @Override
    public boolean isNumberType(String type) {
        if (equalsAnyIgnoreCase(type, "TINYINT", "SMALLINT", "INT", "INTEGER", "YEAR", "MEDIUMINT")) {
            return true;
        }
        if (equalsAnyIgnoreCase(type, "TINYINT UNSIGNED", "SMALLINT UNSIGNED", "INT UNSIGNED", "INTEGER UNSIGNED", "MEDIUMINT UNSIGNED")) {
            return true;
        }
        if (equalsAnyIgnoreCase(type, "TINYINT ZEROFILL", "SMALLINT ZEROFILL", "INT ZEROFILL", "INTEGER ZEROFILL", "MEDIUMINT ZEROFILL")) {
            return true;
        }
        if (equalsAnyIgnoreCase(type, "BIGINT", "BIGINT UNSIGNED", "BIGINT ZEROFILL")) {
            return true;
        }
        if (equalsAnyIgnoreCase(type, "FLOAT", "FLOAT UNSIGNED", "FLOAT ZEROFILL")) {
            return true;
        }
        if (equalsAnyIgnoreCase(type, "DOUBLE", "DOUBLE UNSIGNED", "DOUBLE ZEROFILL", "DOUBLE PRECISION", "DOUBLE PRECISION UNSIGNED",
                "DOUBLE PRECISION ZEROFILL", "REAL", "REAL UNSIGNED", "REAL ZEROFILL")) {
            return true;
        }
        return false;
    }

}
