package com.chalon.mywebfluxdemo;

import com.chalon.mywebfluxdemo.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author wei.peng
 */
public class Client {

    public static void main(String[] args) {
        // 调用服务器地址
        WebClient webClient = WebClient.create("http://127.0.0.1:6688");

        // 根据id查询
        String id = "1";
        User userResult = webClient.get().uri("/users/{id}", id)
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(User.class)
                .block();
        System.out.println(userResult.getName());

        // 添加用户
        User user = new User("Angela", "nv", 23);
        Mono<Void> result = webClient.post().uri("/saveuser")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(user)
                .retrieve().bodyToMono(Void.class);

        // 查询所有
        Flux<User> usersResult = webClient.get().uri("/users")
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(User.class);
        usersResult.map(stu -> stu.getName())
                .buffer().doOnNext(System.out::println).blockFirst();
    }

}
