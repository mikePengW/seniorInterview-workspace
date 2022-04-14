package com.chalon.boot.demo;

import com.ververica.cdc.connectors.oracle.OracleSource;
import com.ververica.cdc.connectors.oracle.table.StartupOptions;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Properties;

public class CisOracle {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.setProperty("database.tablename.case.insensitive", "false");
        props.setProperty("database.connection.adapter", "logminer"); //xstream  logminer
//        // 要同步快，这个配置必须加，不然非常慢
        props.setProperty("log.mining.strategy", "online_catalog");
        props.setProperty("log.mining.continuous.mine", "false");
        props.setProperty("database.pdb.name", "drspdb");

        SourceFunction<String> sourceFunction = OracleSource.<String>builder()
                .hostname("10.124.130.201")
                .port(1521)
                .database("ora19c") // monitor XE database
                .schemaList("GAS_MB") // monitor inventory schema
                .tableList("GAS_MB.SCS_INNER_ROLE") // monitor products table
                .debeziumProperties(props)
                .username("c##flinkuser")
                .password("flinkpw")
                .startupOptions(StartupOptions.initial())
                .deserializer(new JsonDebeziumDeserializationSchema()) // converts SourceRecord to JSON String
                .build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // env.addSource(sourceFunction).print().setParallelism(1); // use parallelism 1 for sink to keep message ordering
        env.addSource(sourceFunction).process(null).setParallelism(1);

        env.execute("OracleSource Demo");
    }
}
