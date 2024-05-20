package com.chalon.boot.mybatisplus;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author wei.peng wrote on 2024-05-20
 * @version 1.0
 */
@Component
public class MyInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) {
        return null;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return new StatementHandlerWrap((StatementHandler) target); //此处只演示StatementHandler，装饰其他对象也会造成同样异常
        }
        return target;
    }

    private static class StatementHandlerWrap implements StatementHandler {
        private final StatementHandler h; //模拟jdk动态代理后的目标对象属性名

        private StatementHandlerWrap(StatementHandler statementHandler) {
            this.h = statementHandler;
        }

        @Override
        public Statement prepare(Connection connection, Integer timeout) throws SQLException {
            //增强代码
            return h.prepare(connection, timeout);
        }

        @Override
        public void parameterize(Statement statement) throws SQLException {
            h.parameterize(statement);
        }

        @Override
        public void batch(Statement statement) throws SQLException {
            h.batch(statement);
        }

        @Override
        public int update(Statement statement) throws SQLException {
            return h.update(statement);
        }

        @Override
        public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
            return h.query(statement, resultHandler);
        }

        @Override
        public <E> Cursor<E> queryCursor(Statement statement) throws SQLException {
            return h.queryCursor(statement);
        }

        @Override
        public BoundSql getBoundSql() {
            return h.getBoundSql();
        }

        @Override
        public ParameterHandler getParameterHandler() {
            return h.getParameterHandler();
        }
    }

}
