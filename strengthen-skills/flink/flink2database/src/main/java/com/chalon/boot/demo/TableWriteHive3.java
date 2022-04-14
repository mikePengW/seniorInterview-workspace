package com.chalon.boot.demo;

import com.ververica.cdc.connectors.oracle.table.StartupOptions;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.apache.commons.lang.SystemUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.*;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.hive.HiveCatalog;
import org.apache.flink.types.Row;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.Duration;


public class TableWriteHive3 {

    public static final Logger LOGGER = LoggerFactory.getLogger(TableWriteHive3.class);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
       // System.setProperty("hadoop.home.dir", "C:\\work\\soft-env\\hadoop-2.6.5");
        //ZK 认证
        String confPath ="C:\\code\\dmsp\\dp-micro-service\\feign-serve-provider\\src\\main\\resources\\";
        if(SystemUtils.IS_OS_LINUX){
            System.setProperty("java.security.krb5.conf", "/app/krb5.conf");
            System.setProperty("java.security.auth.login.config", "/app/jaas.conf");
            System.setProperty("zookeeper.server.principal", "zookeeper/hadoop.hadoop.com");
        } else {
            System.setProperty("java.security.krb5.conf", "C:\\workspace_gaojs\\kafka-examples\\src\\main\\resources\\krb5.conf");
            System.setProperty("zookeeper.server.principal", "zookeeper/hadoop.hadoop.com");
            System.setProperty("java.security.auth.login.config", "C:\\workspace_gaojs\\kafka-examples\\src\\main\\resources\\kafka.jaas.conf");
        }
        //指定端口创建本地上下文环境
    //    org.apache.flink.configuration.Configuration conf1 = new org.apache.flink.configuration.Configuration();
//        //不给端口号默认会调用底层8081
      //  conf1.setString(String.valueOf(RestOptions.BIND_PORT), "18888");

         StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
      //  StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.createLocalEnvironment(conf1);
        Field field = bsEnv.getClass().getSuperclass().getDeclaredField("configuration");
        field.setAccessible(true);
        Configuration configuration = (Configuration) field.get(bsEnv);
        configuration.setString("rest.bind-port", "23083");
        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();

        StreamTableEnvironment tEnv = StreamTableEnvironment.create(bsEnv, bsSettings);
        tEnv.getConfig().getConfiguration().set(ExecutionCheckpointingOptions.CHECKPOINTING_MODE, CheckpointingMode.EXACTLY_ONCE);
        tEnv.getConfig().getConfiguration().set(ExecutionCheckpointingOptions.CHECKPOINTING_INTERVAL, Duration.ofSeconds(10L));
        tEnv.getConfig().getConfiguration().set(ExecutionCheckpointingOptions.CHECKPOINTING_TIMEOUT, Duration.ofSeconds(10L));

        try {
            //hive认证
            org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
            conf.addResource(new Path(confPath+"/core-site.xml"));
            conf.addResource(new Path(confPath+"/hdfs-site.xml"));
            conf.addResource(new Path(confPath+"/hive-site.xml"));
            conf.addResource(new Path(confPath+"/yarn-site.xml"));
            conf.set("hadoop.security.authentication", "Kerberos");
            UserGroupInformation.loginUserFromKeytab("dp_omm", confPath+"/user.keytab");
            tEnv.getConfig().getConfiguration().setString("table.exec.hive.fallback-mapred-writer", "false");
            //表示该 Hive 表可以作为 Source
            tEnv.getConfig().getConfiguration().setString("stream-source.enable", "true");
            tEnv.getConfig().getConfiguration().setString("stream-source.monitor-interval", "1 m");
            //设置程序一直运行
            tEnv.getConfig().getConfiguration().setBoolean("table.dynamic-table-options.enabled", true);
            tEnv.getConfig().getConfiguration().setBoolean("sink.shuffle-by-partition.enable", true);

            HiveCatalog hive = new HiveCatalog(
                    "dp",              // catalog name
                    "default",                // default database
                    confPath, // Hive config (hive-site.xml) directory
                    //  "../../../develop",
                    "3.1.0"                   // Hive version
            );
            //注册，使用
            tEnv.registerCatalog("dp", hive);
            tEnv.useCatalog("dp");
            tEnv.getConfig().setSqlDialect(SqlDialect.DEFAULT);
            tEnv.executeSql("drop table IF EXISTS kafkasource ");
        //   tEnv.executeSql("drop table IF EXISTS iceberg.hivesink");
//            tEnv.executeSql(CreateTableString.CIS_C_MP_RELA);
//            tEnv.executeSql(CreateTableString.TEST);

            tEnv.executeSql("drop table IF EXISTS oracle2kafkasink ");
            tEnv.executeSql("CREATE   TABLE if not exists oracle2kafkasink (\n" +
                    "id STRING,\n" +
                    "name BIGINT,\n" +
                    "t BIGINT,\n" +
                    "PRIMARY KEY (id) NOT ENFORCED \n" +
                    ") WITH (\n" +
                    "'connector' = 'upsert-kafka',\n" +
                    "'topic' = 'dp_rzkj_test_2',\n" +
                    "'properties.bootstrap.servers' = '172.25.2.120:21007,172.25.2.124:21007,172.25.2.30:21007,172.25.2.43:21007,172.25.2.89:21007',\n" +
                    "'properties.group.id' = 'test32dp',\n" +
                    "'properties.security.protocol' = 'SASL_PLAINTEXT',\n" +
                    "'properties.sasl.kerberos.service.name' = 'kafka',\n" +
                    "'key.format'='json',\n"+
                    "'value.format'='json',\n"+
                    "'value.json.fail-on-missing-field'='false'\n" +
                    ")");

            tEnv.executeSql("CREATE   TABLE if not exists dp_rzkj_test_3 (\n" +
                    "id STRING,\n" +
                    "name STRING,\n" +
                    "t STRING,\n" +
                    "PRIMARY KEY (id) NOT ENFORCED \n" +
                    ") WITH (\n" +
                    "'connector' = 'upsert-kafka',\n" +
                    "'topic' = 'dp_rzkj_test_3',\n" +
                    "'properties.bootstrap.servers' = '172.25.2.120:21007,172.25.2.124:21007,172.25.2.30:21007,172.25.2.43:21007,172.25.2.89:21007',\n" +
                    "'properties.group.id' = 'test32dp',\n" +
                    "'properties.security.protocol' = 'SASL_PLAINTEXT',\n" +
                    "'properties.sasl.kerberos.service.name' = 'kafka',\n" +
                    "'key.format'='json',\n"+
                    "'value.format'='json',\n"+
                    "'value.json.fail-on-missing-field'='false'\n" +
                    ")");
            tEnv.executeSql("CREATE   TABLE if not exists oraclekafkasource (\n" +
                    "id STRING,\n" +
                    "name BIGINT,\n" +
                    "t BIGINT\n" +
                    //  "PRIMARY KEY (id) NOT ENFORCED \n" +
                    ") WITH (\n" +
                    "'connector' = 'kafka',\n" +
                    "'topic' = 'dp_rzkj_test_2',\n" +
                    "'properties.bootstrap.servers' = '172.25.2.120:21007,172.25.2.124:21007,172.25.2.30:21007,172.25.2.43:21007,172.25.2.89:21007',\n" +
                    "'properties.group.id' = 'test32dp1111',\n" +
                    "'properties.security.protocol' = 'SASL_PLAINTEXT',\n" +
                    "'properties.sasl.kerberos.service.name' = 'kafka',\n" +
                    "'scan.startup.mode' = 'earliest-offset',\n" + //latest-offset  earliest-offset
                    "'format' = 'json',\n" +  //json
                    "'value.format'='json',\n"+
                    "'json.ignore-parse-errors' = 'true' \n" +
                    ")");
            tEnv.getConfig().setSqlDialect(SqlDialect.HIVE);
            tEnv.useCatalog("dp");
            tEnv.executeSql("CREATE TABLE if not exists iceberg.hivesinkfrom (\n" +
                    "id STRING,\n" +
                    "name BIGINT,\n" +
                    "t BIGINT\n" +
                    ")\n" +
                    "STORED AS parquet  TBLPROPERTIES (\n" +
//                    "'partition.time-extractor.timestamp-pattern'='$dt',\n" +
                    "'sink.partition-commit.trigger'='partition-time',\n" +
                    "'sink.partition-commit.delay'='0s',\n" +
                    "'sink.partition-commit.policy.kind'='metastore,success-file'\n" +
                    ")");

            System.out.println("------1-");
            tEnv.getConfig().setSqlDialect(SqlDialect.DEFAULT);
            StatementSet statementSet = tEnv.createStatementSet();
          //  tEnv.executeSql("select * from iceberg.hivesinkfrom").print();

            //oracle到kafka
            statementSet.addInsertSql("insert into  oracle2kafkasink select RELA_SORT_CODE as id ,CONS_ID as name,MP_ID as t from C_MP_RELA");
           //测试多表写入
          //  statementSet.addInsertSql("insert into dp_rzkj_test_3  select id,name,age as t from test");
            //测试oracle直达HIVE
          //  statementSet.addInsertSql("insert into  iceberg.hivesinkfrom select RELA_SORT_CODE as id ,CONS_ID as name,MP_ID as t from C_MP_RELA");

            //kafka到hive
          //   statementSet.addInsertSql("insert into  iceberg.hivesinkfrom select  id , name, t from oraclekafkasource");

            Table oracle2kafkasink = tEnv.from("C_MP_RELA");
            DataStream<Row> dataStream = tEnv.toChangelogStream(oracle2kafkasink);
            tEnv.createTemporaryView("hiveSinkTemp", dataStream);

            // dataStream.print();
            //  tEnv.executeSql("select * from iceberg.hivesinkfrom");
            statementSet.addInsertSql("insert into  iceberg.hivesinkfrom select RELA_SORT_CODE as id ,CONS_ID as name,MP_ID as t from hiveSinkTemp");
         //   statementSet.addInsert("iceberg.hivesinkfrom",oraclekafkasource);
           TableResult execute = statementSet.execute();

            System.out.println("---------");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("任务失败" + e.getMessage());
        }
    }
}
