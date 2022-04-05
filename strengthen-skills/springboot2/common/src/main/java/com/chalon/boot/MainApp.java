package com.chalon;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author wei.peng
 */
@EnableAdminServer
@MapperScan("com.chalon.mapper")
@ServletComponentScan(basePackages = "com.chalon")
@SpringBootApplication(exclude = RedisAutoConfiguration.class)
public class MainApp {
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

}
