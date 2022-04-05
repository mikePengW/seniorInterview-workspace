package com.chalon.boot.controller;

import com.chalon.hello.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author wei.peng
 */
@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String sayHello() {
        String s = helloService.sayHello("小芳");
        return s;
    }

    @PostConstruct
    public void world(){
        System.out.println("world.");
    }

}
