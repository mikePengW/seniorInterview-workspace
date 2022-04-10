package com.chalon.boot.utils.script.support;

import com.chalon.boot.utils.script.support.db.MyDatabaseSupport;
import com.chalon.boot.utils.script.support.db.OracleDatabaseSupport;

public class DatabaseSupports {

    public static DatabaseSupport getDatabaseSupport(String jdbcUrl) {
        if (jdbcUrl.contains("mysql")) {
            return mySqlDatabaseSupport;
        }
        if (jdbcUrl.contains("oracle")) {
            return oracleDatabaseSupport;
        }
        throw new RuntimeException("不支持的数据库类型: " + jdbcUrl);
    }

    private static DatabaseSupport mySqlDatabaseSupport = new MyDatabaseSupport();

    private static DatabaseSupport oracleDatabaseSupport = new OracleDatabaseSupport();

}
