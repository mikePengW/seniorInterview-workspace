package com.chalon.boot.utils.script.support;

public interface DatabaseSupport {

	String getSelectOne(String tableName);

	String toHiveColumn(String columnName, String columnType);

	String toSqoopColumn(String columnName, String columnType);

	boolean isNumberType(String type);
}
