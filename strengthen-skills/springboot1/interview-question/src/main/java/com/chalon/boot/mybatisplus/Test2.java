package com.chalon.boot.mybatisplus;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author wei.peng wrote on 2024-05-20
 * @version 1.0
 */
public class Test2 {

    public interface PluginProxyAdapter {
        Object theProxyObject();
    }

    // com.baomidou.mybatisplus.core.toolkit.PluginUtils#realTarget，优先判断PluginProxyAdapter
    public static <T> T realTarget(Object target) {
        if (target instanceof PluginProxyAdapter) {
            return realTarget(((PluginProxyAdapter) target).theProxyObject());
        }
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        }
        return (T) target;
    }

    private static class StatementHandlerWrap implements StatementHandler, PluginProxyAdapter {
        private final StatementHandler h; //模拟jdk动态代理后的目标对象属性名

        private StatementHandlerWrap(StatementHandler statementHandler) {
            this.h = statementHandler;
        }

        @Override
        public Object theProxyObject() {
            return this.h;
        }



        @Override
        public Statement prepare(Connection connection, Integer transactionTimeout) throws SQLException {
            return null;
        }

        @Override
        public void parameterize(Statement statement) throws SQLException {

        }

        @Override
        public void batch(Statement statement) throws SQLException {

        }

        @Override
        public int update(Statement statement) throws SQLException {
            return 0;
        }

        @Override
        public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
            return List.of();
        }

        @Override
        public <E> Cursor<E> queryCursor(Statement statement) throws SQLException {
            return null;
        }

        @Override
        public BoundSql getBoundSql() {
            return null;
        }

        @Override
        public ParameterHandler getParameterHandler() {
            return null;
        }
    }

}
