package com.chalon.mywebfluxdemo.handler;

import com.chalon.mywebfluxdemo.entity.User;
import com.chalon.mywebfluxdemo.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * 1 handler 实际操作方法
 *
 * @author wei.peng
 */
public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        int userId = Integer.valueOf(request.pathVariable("id"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<User> userMono = this.userService.getUserById(userId);
        return userMono
                .flatMap(person -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON).body(fromObject(person)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        Flux<User> users = this.userService.getAllUser();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(users, User.class);
    }

    public Mono<ServerResponse> saveUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        userMono.subscribe(System.out::println);
        return ServerResponse.ok().build(this.userService.saveUserInfo(userMono));
    }

}
