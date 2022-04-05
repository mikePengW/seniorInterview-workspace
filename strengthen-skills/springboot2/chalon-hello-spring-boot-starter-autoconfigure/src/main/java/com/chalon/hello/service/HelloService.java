package com.chalon.hello.service;

import com.chalon.hello.bean.HelloProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 默认不要放在容器中
 *
 * @author wei.peng
 */
public class HelloService {

    @Autowired
    HelloProperties helloProperties;

    public String sayHello(String userName) {
        return helloProperties.getPrefix() + "：" + userName + "》" + helloProperties.getSuffix();
    }

}
