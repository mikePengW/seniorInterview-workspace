package com.chalon.jpa;

import com.chalon.jpa.entity.Person;
import com.chalon.jpa.repository.PersonRepository;
import com.chalon.jpa.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.persistence.criteria.*;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author wei.peng
 */
@SpringBootTest
public class SpringDataTest {

    @Autowired
    private GenericWebApplicationContext genericWebApplicationContext;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;

    @Test
    public void testCustomRepositoryMethod() {
        personRepository.test();
    }

    @Test
    public void testJpaSpecificationExecutor() {
        int pageNo = 3 - 1;
        int pageSize = 5;
        PageRequest pageable = PageRequest.of(pageNo, pageSize);

        Specification<Person> specification = new Specification<Person>() {
            @Override
            public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path path = root.get("id");
                Predicate predicate = criteriaBuilder.gt(path, 5);
                return predicate;
            }
        };

        Page<Person> page = personRepository.findAll(specification, pageable);

        System.out.println("总记录数：" + page.getTotalElements());
        System.out.println("当前第几页：" + (page.getNumber() + 1));
        System.out.println("总页数：" + page.getTotalPages());
        System.out.println("当前页面的 List：" + page.getContent());
        System.out.println("当前页面的记录数：" + page.getNumberOfElements());

    }

    @Test
    public void testJpaRepository() {
        Person person = new Person();
        person.setBirth(new Date());
        person.setEmail("xyz@chalon.com");
        person.setLastName("z");
        person.setId(28);

        Person person2 = personRepository.saveAndFlush(person);

        System.out.println(person == person2);
    }

    @Test
    public void testPagingAndSortingRepository() {
        int pageNo = 6 - 1;
        int pageSize = 5;

        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "id");
        Sort.Order order2 = new Sort.Order(Sort.Direction.ASC, "email");
        Sort sort = Sort.by(order1, order2);

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Person> page = personRepository.findAll(pageable);

        System.out.println("总记录数：" + page.getTotalElements());
        System.out.println("当前第几页：" + (page.getNumber() + 1));
        System.out.println("总页数：" + page.getTotalPages());
        System.out.println("当前页面的 List：" + page.getContent());
        System.out.println("当前页面的记录数：" + page.getNumberOfElements());
    }

    @Test
    public void testCrudRepository() {
        List<Person> persons = new ArrayList<>();

        for (int i = 'a'; i <= 'z'; i++) {
            Person person = new Person();
            person.setAddressId(i + 1);
            person.setBirth(new Date());
            person.setEmail((char) i + "" + (char) i + "@chalon.com");
            person.setLastName((char) i + "" + (char) i);

            persons.add(person);
        }

        personService.savePersons(persons);
    }

    @Test
    public void testModifying() {
        personService.updatePersonEmail("sss@chalon.com", 1);
    }

    @Test
    public void testNativeQuery() {
        long count = personRepository.getTotalCount();
        System.out.println(count);
    }

    @Test
    public void testQueryAnnotationLikeParam2() {
        List<Person> persons = personRepository.testQueryAnnotationLikeParam2("bb", "A");
        System.out.println(persons);
    }

    @Test
    public void testQueryAnnotationLikeParam() {
        List<Person> persons = personRepository.testQueryAnnotationLikeParam("A", "bb");
        System.out.println(persons);
    }

    @Test
    public void testQueryAnnotationParams2() {
        List<Person> persons = personRepository.testQueryAnnotationParams2("aa@chalon.com", "AA");
        System.out.println(persons);
    }

    @Test
    public void testQueryAnnotationParams1() {
        List<Person> persons = personRepository.testQueryAnnotationParams1("AA", "aa@chalon.com");
        System.out.println(persons);
    }

    @Test
    public void testQueryAnnotation() {
        Person person = personRepository.getMaxIdPerson();
        System.out.println(person);
    }

    @Test
    public void testKeyWords2() {
        List<Person> persons = personRepository.getByAddress_IdGreaterThan(1);
        System.out.println(persons);
    }

    @Test
    public void testKeyWords() {
        List<Person> persons = personRepository.getByLastNameStartingWithAndIdLessThan("X", 10);
        System.out.println(persons);

        persons = personRepository.getByLastNameEndingWithAndIdLessThan("X", 10);
        System.out.println(persons);

        persons = personRepository.getByEmailInAndBirthLessThan(Arrays.asList("xx@chalon.com", "ix@chalon.com"), new Date());
        System.out.println(persons.size());
    }

    @Test
    public void testHelloWorldSpringData() {
        System.out.println(personRepository.getClass().getName());

        Person person = personRepository.getByLastName("AA");
        System.out.println(person);
    }

    @Test
    public void testJpa() {

    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = genericWebApplicationContext.getBean("dataSource", DataSource.class);
        System.out.println(dataSource.getConnection());
    }

    private void printBeans(GenericWebApplicationContext genericWebApplicationContext) {
        String[] definitionNames = genericWebApplicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }

}
