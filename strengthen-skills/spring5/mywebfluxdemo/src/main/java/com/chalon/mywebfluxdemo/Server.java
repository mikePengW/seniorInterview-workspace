package com.chalon.mywebfluxdemo;

import com.chalon.mywebfluxdemo.handler.UserHandler;
import com.chalon.mywebfluxdemo.service.UserService;
import com.chalon.mywebfluxdemo.service.impl.UserServiceImpl;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

/**
 * @author wei.peng
 */
public class Server {

    // 4 调用
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.createReactorServer();
        System.out.println("enter to exit");
        System.in.read();
    }

    // 2 创建Router路由
    public RouterFunction<ServerResponse> routingFunction() {
        // 创建handler对象
        UserService userService = new UserServiceImpl();
        UserHandler handler = new UserHandler(userService);
        // 设置路由
        return RouterFunctions.route()
                .GET("/users/{id}", accept(APPLICATION_JSON), handler::getUserById)
                .GET("/users", accept(APPLICATION_JSON), handler::getAllUsers)
                .POST("/saveuser", handler::saveUser)
                .build();
    }

    // 3 创建服务器完成适配
    public void createReactorServer() {
        // 路由和handler适配
        RouterFunction<ServerResponse> route = routingFunction();
        HttpHandler httpHandler = toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        // 创建服务器
        HttpServer httpServer = HttpServer.create().host("127.0.0.1").port(6688);
        httpServer.handle(adapter).bindNow();
    }

}
