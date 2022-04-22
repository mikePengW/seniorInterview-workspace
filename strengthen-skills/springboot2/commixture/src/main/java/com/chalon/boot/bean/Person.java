package com.chalon.boot.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author wei.peng
 */
@Data
public class Person {
    private String username;
    private int age;
    private Date birth;
    private Pet pet;

}
