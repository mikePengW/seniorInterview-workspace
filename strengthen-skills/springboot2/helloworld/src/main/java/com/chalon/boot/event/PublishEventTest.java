package com.chalon.boot.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wei.peng wrote on 2024-01-08
 * @version 1.0
 */
public class PublishEventTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("test.config.xml");
        applicationContext.publishEvent(new BaseEvent<>("Hello, World!"));

    }

    @EventListener
    public void handleEvent(BaseEvent<PerformParam> event) {

    }

}
