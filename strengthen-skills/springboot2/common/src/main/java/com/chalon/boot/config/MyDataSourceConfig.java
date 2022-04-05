package com.chalon.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author wei.peng
 */
@Deprecated
//@Configuration
public class MyDataSourceConfig {

    // 默认的自动配置是判断容器中没有才会配 @ConditionalOnMissingBean(DataSource.class)
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setFilters("stat,wall");
        druidDataSource.setMaxActive(10);
        return druidDataSource;
    }

    /**
     * 配置 druid 的监控页功能
     *
     * @return
     */
//    @Bean
    public ServletRegistrationBean statViewServlet() {
        StatViewServlet statViewServlet = new StatViewServlet();
        ServletRegistrationBean<StatViewServlet> servletRegBean = new ServletRegistrationBean<>(statViewServlet);
        servletRegBean.setUrlMappings(Arrays.asList("/druid/*"));
        servletRegBean.addInitParameter("loginUsername", "admin");
        servletRegBean.addInitParameter("loginPassword", "123456");
        return servletRegBean;
    }

    /**
     * WebStatFilter 用于采集 web-jdbc 关联监控的数据。
     *
     * @return
     */
//    @Bean
    public FilterRegistrationBean webStatFilter() {
        WebStatFilter webStatFilter = new WebStatFilter();
        FilterRegistrationBean<WebStatFilter> filterRegBean = new FilterRegistrationBean<WebStatFilter>(webStatFilter);
        filterRegBean.setUrlPatterns(Arrays.asList("/*"));
        filterRegBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegBean;
    }

}
