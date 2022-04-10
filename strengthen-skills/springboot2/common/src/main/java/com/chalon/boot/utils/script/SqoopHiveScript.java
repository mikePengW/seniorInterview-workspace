package com.chalon.boot.utils.script;

import com.chalon.boot.utils.script.support.DatabaseSupport;
import com.chalon.boot.utils.script.support.DatabaseSupports;
import com.chalon.boot.utils.script.support.TableMeta;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.JdbcUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.equalsAnyIgnoreCase;

public class SqoopHiveScript implements Runnable {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String NewLine = "\n";

    private String propsFile;

    private String jdbcDriverClassName;

    private String jdbcUrl;

    private String jdbcUsername;

    private String jdbcPassword;

    private String jdbcDatabase;

    private String hdfsNameNode;

    private String hdfsOutputDir;

    private boolean hdfsOutputDirDelete;

    private String hiveScriptFile;

    private boolean hiveDropTable;

    private String hiveFileType;

    private String sqoopScriptFile;

    private int sqoopNumMappers;

    private int sqoopNumFiles = 3;

    private String baseTime;

    private SingleConnectionDataSource dataSource;

    private DatabaseSupport databaseSupport;

    private Set<String> whitelist;

    private Set<String> blacklist;

    public void setPropsFile(String propsFile) {
        this.propsFile = propsFile;
    }

    private void initRun() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(propsFile)) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("读取配置文件错误: " + propsFile, e);
        }
        jdbcDriverClassName = props.getProperty("jdbcDriverClassName");
        jdbcUrl = props.getProperty("jdbcUrl");
        jdbcUsername = props.getProperty("jdbcUsername");
        jdbcPassword = props.getProperty("jdbcPassword");
        jdbcDatabase = props.getProperty("jdbcDatabase");
        hdfsNameNode = props.getProperty("hdfsNameNode");
        hdfsOutputDir = props.getProperty("hdfsOutputDir");
        hiveScriptFile = props.getProperty("hiveScriptFile");
        sqoopScriptFile = props.getProperty("sqoopScriptFile");

        Preconditions.checkNotNull(jdbcDriverClassName, "参数jdbcDriverClassName为空.");
        Preconditions.checkNotNull(jdbcUrl, "参数jdbcUrl为空.");
        Preconditions.checkNotNull(jdbcUsername, "参数jdbcUsername为空.");
        Preconditions.checkNotNull(jdbcPassword, "参数jdbcPassword为空.");
        Preconditions.checkNotNull(jdbcDatabase, "参数jdbcDatabase为空.");
        Preconditions.checkNotNull(hdfsNameNode, "参数hdfsNameNode为空.");
        Preconditions.checkNotNull(hdfsOutputDir, "参数hdfsOutputDir为空.");
        Preconditions.checkNotNull(hiveScriptFile, "参数hiveScriptFile为空.");
        Preconditions.checkNotNull(sqoopScriptFile, "参数hiveScriptFile为空.");

        if (hdfsOutputDir.endsWith("/") == false) {
            hdfsOutputDir = hdfsOutputDir + "/";
        }
        hdfsOutputDirDelete = Boolean.parseBoolean(props.getProperty("hdfsOutputDirDelete", "false"));
        hiveDropTable = Boolean.parseBoolean(props.getProperty("hiveDropTable", "false"));
        sqoopNumMappers = NumberUtils.toInt(props.getProperty("sqoopNumMappers"), 1);
        sqoopNumFiles = NumberUtils.toInt(props.getProperty("sqoopNumFiles"), 3);

        hiveFileType = props.getProperty("hiveFileType", "PARQUET");
        if (equalsAnyIgnoreCase(hiveFileType, "AVRO", "PARQUET") == false) {
            throw new RuntimeException("不支持的文件类型: " + hiveFileType);
        }
        if (StringUtils.isNotEmpty(props.getProperty("whitelist"))) {
            whitelist = Files.readLines(new File(props.getProperty("whitelist")), Charsets.UTF_8,
                    new LineProcessor<Set<String>>() {

                        Set<String> t = Sets.newHashSet();

                        @Override
                        public Set<String> getResult() {
                            return t;
                        }

                        @Override
                        public boolean processLine(String line) throws IOException {
                            t.add(StringUtils.trimToEmpty(line));
                            return true;
                        }
                    });
            logger.info("whitelist size: {}.", whitelist.size());

        }
        if (StringUtils.isNotEmpty(props.getProperty("blacklist"))) {
            blacklist = Files.readLines(new File(props.getProperty("blacklist")), Charsets.UTF_8,
                    new LineProcessor<Set<String>>() {

                        Set<String> t = Sets.newHashSet();

                        @Override
                        public Set<String> getResult() {
                            return t;
                        }

                        @Override
                        public boolean processLine(String line) throws IOException {
                            t.add(StringUtils.trimToEmpty(line));
                            return true;
                        }
                    });
            logger.info("blacklist size: {}.", blacklist.size());
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
        baseTime = df.format(new Date());
        logger.info("Database: {} {}.", jdbcDriverClassName, jdbcUrl);
        dataSource = new SingleConnectionDataSource();
        dataSource.setSuppressClose(true);
        dataSource.setDriverClassName(jdbcDriverClassName);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        databaseSupport = DatabaseSupports.getDatabaseSupport(jdbcUrl);
    }

    @Override
    public void run() {
        try {
            initRun();
            List<String> tableNames = getAllTableNames();
            logger.info("tableNames size: {}.", tableNames.size());
            List<TableMeta> tables = Lists.newArrayList();
            for (String tableName : tableNames) {
                TableMeta e = new TableMeta();
                e.setTableName(tableName);
                tables.add(e);
            }
            for (TableMeta tableMeta : tables) {
                List<String> primaryKey = getTablePrimaryKey(tableMeta.getTableName());
                tableMeta.setPrimaryKey(primaryKey);
            }
            for (TableMeta tableMeta : tables) {
                List<Pair<String, String>> columns = getTableColumns(tableMeta.getTableName());
                tableMeta.setColumns(columns);
            }
            Iterator<TableMeta> it = tables.iterator();
            while (it.hasNext()) {
                TableMeta tb = it.next();
                if (tb.getColumns().isEmpty()) {
                    logger.info("table is empty: {}.", tb.getTableName());
                    it.remove();
                }
            }
            logger.info("tables size: {}.", tables.size());
            createAllSqoopTableScript(tables);
            createAllHiveTableScript(tables);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeRun();
        }

    }

    private void closeRun() {
        if (dataSource != null) {
            dataSource.destroy();
        }
    }

    /**
     * 获取所有表名
     */
    private List<String> getAllTableNames() {
        List<String> tabs = Lists.newArrayList();

        if (whitelist.size() == 0) {
            Connection con = null;
            ResultSet rs = null;
            try {
                con = dataSource.getConnection();
                DatabaseMetaData metaData = con.getMetaData();
                rs = metaData.getTables(jdbcDatabase, null, "%", new String[]{"TABLE"});
                while (rs.next()) {
                    String tn = rs.getString("TABLE_NAME");
                    if (whitelist != null && whitelist.contains(tn)) {
                        tabs.add(tn);
                        continue;
                    }
                    if (blacklist != null && blacklist.contains(tn) == false) {
                        tabs.add(tn);
                        continue;
                    }
                    if (whitelist == null && blacklist == null) {
                        tabs.add(tn);
                        continue;
                    }
                }
            } catch (SQLException e) {
                logger.error("查询表名异常.", e);
                throw new RuntimeException(e);
            } finally {
                JdbcUtils.closeResultSet(rs);
                JdbcUtils.closeConnection(con);
            }
        } else {
            tabs.addAll(whitelist);
        }
        return tabs;
    }

    /**
     * 获取指定表字段及类型
     */
    private List<Pair<String, String>> getTableColumns(String tableName) {
        List<Pair<String, String>> columns = Lists.newArrayList();
        String sql = databaseSupport.getSelectOne(tableName);
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean hasData = false;
        try {
            con = dataSource.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                hasData = true;
            }
            if (hasData) {
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String name = rsmd.getColumnName(i);
                    String type = rsmd.getColumnTypeName(i);
                    columns.add(Pair.of(name, type));
                }
            }
        } catch (SQLException e) {
            logger.error("查询指定表列信息异常.", e);
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(stmt);
            JdbcUtils.closeConnection(con);
        }
        return columns;

    }

    private List<String> getTablePrimaryKey(String tableName) {
        List<String> pks = Lists.newArrayList();
        Connection con = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            DatabaseMetaData metadata = con.getMetaData();
            rs = metadata.getPrimaryKeys(jdbcDatabase, null, tableName);
            if (rs.next()) {
                pks.add(rs.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            logger.error("查询表主键异常.", e);
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeConnection(con);
        }
        return pks;
    }

    private void createAllSqoopTableScript(List<TableMeta> tables) {
        // 初始化List<List>
        List<List<TableMeta>> tablesParts = Lists.newArrayList();
        for (int i = 0; i < sqoopNumFiles; i++) {
            tablesParts.add(Lists.newArrayList());
        }
        // 从0个List开始添加
        int index = 0;
        for (TableMeta t : tables) {
            // 如果循环到尾了，重头开始添加
            if (index == tablesParts.size()) {
                index = 0;
            }
            tablesParts.get(index).add(t);
            // 下次循环添加下一个
            index++;
        }
        for (int i = 0; i < tablesParts.size(); i++) {
            List<TableMeta> tablesPart = tablesParts.get(i);
            createPartSqoopTableScript(i, tablesPart);
        }
    }

    private void createPartSqoopTableScript(int part, List<TableMeta> tables) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(sqoopScriptFile + "-" + part + ".sh", false);
            for (int i = 0; i < tables.size(); i++) {
                TableMeta tableMeta = tables.get(i);
                String script = createOneSqoopTableScript(tables.size(), i + 1, tableMeta);
                fos.write(script.getBytes("UTF-8"));
            }
        } catch (IOException e) {
            logger.error("创建Sqoop脚本失败:" + hiveScriptFile, e);
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

    private String createOneSqoopTableScript(int total, int i, TableMeta tableMeta) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder script = new StringBuilder();
        script.append("echo \"`date`>>>>>>sqoop-import[").append(tableMeta.getTableName()).append("][").append(i)
                .append("-").append(total).append("]<<<<<<\"").append(NewLine);
        script.append("sqoop import");
        appendJdbc(script, tableMeta);
        appendQuery(script, tableMeta);
        appendSplit(script, tableMeta);
        appendOutput(script, tableMeta);
        script.append(" --class-name=" + tableMeta.getTableName() + "_" + sdf.format(new Date()));
        script.append(NewLine);
        script.append(NewLine);
        return script.toString();
    }

    private void appendQuery(StringBuilder script, TableMeta tableMeta) {
        script.append(" --query \"SELECT ");
        for (int i = 0; i < tableMeta.getColumns().size(); i++) {
            Pair<String, String> column = tableMeta.getColumns().get(i);
            script.append(databaseSupport.toSqoopColumn(column.getLeft(), column.getRight()));
            if (i < tableMeta.getColumns().size() - 1) {
                script.append(",");
            }
        }
        script.append(" FROM ").append(tableMeta.getTableName());
        script.append(" WHERE \\$CONDITIONS");
        script.append("\"");
    }

    private void appendJdbc(StringBuilder script, TableMeta tableMeta) {
        script.append(" --connect '").append(jdbcUrl).append("'");
        script.append(" --username '").append(jdbcUsername).append("'");
        script.append(" --password '").append(jdbcPassword).append("'");
    }

    private void appendSplit(StringBuilder script, TableMeta tableMeta) {
        if (canSplit(tableMeta) == false) {
            script.append(" -m 1");
        } else {
            script.append(" -m ").append(sqoopNumMappers);
            script.append(" --split-by ").append(tableMeta.getPrimaryKey().get(0));
        }
    }

    private boolean canSplit(TableMeta tableMeta) {
        if (sqoopNumMappers > 1 && tableMeta.getPrimaryKey() != null && tableMeta.getPrimaryKey().size() == 1
                && databaseSupport.isNumberType(tableMeta.getColumnType(tableMeta.getPrimaryKey().get(0)))) {
            return true;
        } else {
            return false;
        }
    }

    private void appendOutput(StringBuilder script, TableMeta tableMeta) {
        if (hdfsOutputDirDelete) {
            script.append(" --delete-target-dir");
        }
        script.append(" --target-dir ").append(createLocation(tableMeta.getTableName()));
        if (hiveFileType.equals("AVRO")) {
            script.append(" --as-avrodatafile");
        } else if (hiveFileType.equals("PARQUET")) {
            script.append(" --as-parquetfile");
        } else {
            throw new RuntimeException("不支持的文件类型.");
        }
        script.append(" -z");
        script.append(" --bindir /tmp/sqoop-tmp/").append(jdbcDatabase).append("/")
                .append(tableMeta.getTableName().toLowerCase()).append("/");
        script.append(" --outdir /tmp/sqoop-tmp/").append(jdbcDatabase).append("/")
                .append(tableMeta.getTableName().toLowerCase()).append("/");
    }

    private void createAllHiveTableScript(List<TableMeta> tables) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(hiveScriptFile, false);
            String s = "use " + jdbcDatabase + ";" + NewLine + NewLine;
            fos.write(s.getBytes("UTF-8"));
            for (TableMeta table : tables) {
                String script = createOneHiveTableScript(table);
                fos.write(script.getBytes("UTF-8"));
            }
        } catch (IOException e) {
            logger.error("创建Hive脚本失败:" + hiveScriptFile, e);
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

    private String createOneHiveTableScript(TableMeta tableMeta) {
        StringBuilder script = new StringBuilder();
        if (hiveDropTable) {
            script.append("DROP TABLE IF EXISTS ").append(tableMeta.getTableName()).append(";").append(NewLine);
        }
        // 建表语句
        script.append("CREATE EXTERNAL TABLE IF NOT EXISTS ").append(tableMeta.getTableName()).append(" ( ")
                .append(NewLine);
        for (int i = 0; i < tableMeta.getColumns().size(); i++) {
            Pair<String, String> column = tableMeta.getColumns().get(i);
            script.append("    ");
            script.append(databaseSupport.toHiveColumn(column.getLeft(), column.getRight()));
            if (i < tableMeta.getColumns().size() - 1) {
                script.append(",");
            }
            script.append(NewLine);
        }
        script.append(")").append(NewLine);
        script.append("STORED AS ").append(hiveFileType).append(";").append(NewLine);
        // 修改表的LOCATION
        script.append("ALTER TABLE ").append(tableMeta.getTableName()).append(" SET LOCATION '")
                .append(createHDFSLocation(tableMeta.getTableName())).append("';").append(NewLine);
        script.append(NewLine);
        return script.toString();
    }

    private String createLocation(String tableName) {
        return hdfsOutputDir + jdbcDatabase.toLowerCase() + "/" + tableName.toLowerCase() + "/" + baseTime;
    }

    private String createHDFSLocation(String tableName) {
        return hdfsNameNode + createLocation(tableName);
    }

    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 1) {
            System.err.println("请输入配置文件");
            throw new Exception("配置文件缺失.");
        }
        SqoopHiveScript sqoopHiveScript = new SqoopHiveScript();
        sqoopHiveScript.setPropsFile(args[0]);
        sqoopHiveScript.run();
    }

}