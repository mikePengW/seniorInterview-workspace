package com.chalon.acutuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @author wei.peng
 */
@Component
@Endpoint(id = "myService")
public class MyServiceEndPoint {

    @ReadOperation
    public Map getDockerInfo() {
        // 端点的读操作 http://localhost:8080/actuator/myService
        return Collections.singletonMap("dockerInfo", "docker started......");
    }

    @WriteOperation
    public void stopDocker() {
        System.out.println("docker stopped......");
    }

}
