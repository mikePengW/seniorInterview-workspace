package com.chalon.boot.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wei.peng wrote on 2024-01-08
 * @version 1.0
 */
public class BaseEvent<T> extends ApplicationEvent {
    private final T data;

    public BaseEvent(T source) {
        super(source);
        this.data = source;
    }

    public T getData() {
        return data;
    }

}
