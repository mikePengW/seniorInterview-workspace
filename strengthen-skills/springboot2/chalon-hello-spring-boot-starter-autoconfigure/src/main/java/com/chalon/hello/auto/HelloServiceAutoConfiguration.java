package com.chalon.hello.auto;

import com.chalon.hello.bean.HelloProperties;
import com.chalon.hello.service.HelloService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wei.peng
 */
@Configuration
@EnableConfigurationProperties(HelloProperties.class) // 默认 HelloProperties 放在容器中
public class HelloServiceAutoConfiguration {

    @ConditionalOnMissingBean(HelloService.class)
    @Bean
    public HelloService helloService() {
        HelloService helloService = new HelloService();
        return helloService;
    }

}
