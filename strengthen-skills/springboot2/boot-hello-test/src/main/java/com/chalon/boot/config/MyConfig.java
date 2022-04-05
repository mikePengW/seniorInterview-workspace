package com.chalon.boot.config;

import com.chalon.hello.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wei.peng
 */
@Configuration
public class MyConfig {

    @Bean
    public HelloService helloService() {
         HelloService helloService = new HelloService();

        return helloService;
    }

}
