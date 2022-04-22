package com.chalon.mywebfluxdemo.service;

import com.chalon.mywebfluxdemo.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author wei.peng
 */
public interface UserService {
    Mono<User> getUserById(int id);

    Flux<User> getAllUser();

    Mono<Void> saveUserInfo(Mono<User> userMono);
}
