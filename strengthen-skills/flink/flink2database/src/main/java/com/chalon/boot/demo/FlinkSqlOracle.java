package com.chalon.boot.demo;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;

/**
 * @Author: tancheng
 * @Description:
 * @Date: Created in 2022-03-17 14:23
 * @Modified By:
 */
public class FlinkSqlOracle {

    public static void main(String[] args) {
        EnvironmentSettings build = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        TableEnvironment tableEnvironment = TableEnvironment.create(build);

        String sql ="CREATE TABLE test (\n" +
                "             id STRING,\n" +
                "             name STRING,\n" +
                "             age STRING,\n" +
                "              sex STRING\n" +
                "             ) WITH (\n" +
                "             'connector' = 'oracle-cdc',\n" +
                "             'hostname' = '10.124.130.201',\n" +
                "             'port' = '1521',\n" +
                "             'username' = 'c##flinkuser',\n" +
                "             'password' = 'flinkpw',\n" +
                "             'database-name' = 'ora19c',\n" +
                "             'schema-name' = 'C##FLINKUSER',\n" +
                " 'debezium.database.tablename.case.insensitive'='false',\n"+
                " 'debezium.database.connection.adapter'='logminer',\n"+
                " 'debezium.log.mining.continuous.mine'='false',\n"+
                " 'debezium.log.mining.strategy'='online_catalog',\n"+
                "             'table-name' = 'TEST'\n" +
                "             )";

        tableEnvironment.executeSql(sql);
// 如下两种print数据方式都可以使用
// 方法 1
        TableResult tableResult = tableEnvironment.executeSql("select * from test");
        tableResult.print();

// 方法 2
        TableResult tableResult1 = tableEnvironment.executeSql("CREATE TABLE sink_table (id STRING, name STRING, age STRING,sex STRING) WITH (    'connector' = 'print')");

        //tableResult1.print();
        TableResult tableResult2 = tableEnvironment.executeSql("INSERT INTO sink_table SELECT id, name, age,sex FROM test");

       // tableResult2.print();

        System.out.println("----------22");
        tableEnvironment.executeSql("select id,sex from sink_table").print();

       // tableResult.print();

    }
}
