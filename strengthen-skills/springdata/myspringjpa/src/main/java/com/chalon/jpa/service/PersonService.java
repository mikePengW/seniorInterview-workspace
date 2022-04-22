package com.chalon.jpa.service;

import com.chalon.jpa.entity.Person;
import com.chalon.jpa.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wei.peng
 */
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public void savePersons(List<Person> persons) {
        personRepository.saveAll(persons);
    }

    @Transactional
    public void updatePersonEmail(String email, Integer id) {
        personRepository.updatePersonEmail(id, email);
    }
}
