package com.chalon.jpa.repository.impl;

import com.chalon.jpa.dao.PersonDao;
import com.chalon.jpa.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author wei.peng
 */
public class PersonRepositoryImpl implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void test() {
        Person person = entityManager.find(Person.class, 11);
        System.out.println("-->" + person);
    }

}
