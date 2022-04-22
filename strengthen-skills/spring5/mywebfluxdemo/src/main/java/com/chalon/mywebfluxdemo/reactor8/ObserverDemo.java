package com.chalon.mywebfluxdemo.reactor8;

import java.util.Observable;

/**
 * @author wei.peng
 */
public class ObserverDemo extends Observable {
    public static void main(String[] args) {
        ObserverDemo observer = new ObserverDemo();
        observer.addObserver((o, arg) -> {
            System.out.println("发生变化");
        });
        observer.addObserver((o, arg) -> {
            System.out.println("手动被观察者通知，准备改变");
        });
        observer.setChanged();
        observer.notifyObservers();
    }
}
