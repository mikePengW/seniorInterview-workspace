package com.chalon.jpa.repository;

import com.chalon.jpa.dao.PersonDao;
import com.chalon.jpa.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @author wei.peng
 */
//@RepositoryDefinition(domainClass = Person.class, idClass = Integer.class)
public interface PersonRepository extends JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person>, PersonDao {

    // 根据 lastName 来获取对应的 Person
    Person getByLastName(String lastName);

    // WHERE lastName LIKE ?% AND id < ?
    List<Person> getByLastNameStartingWithAndIdLessThan(String lastName, Integer id);

    // WHERE lastName LIKE %? AND id < ?
    List<Person> getByLastNameEndingWithAndIdLessThan(String lastName, Integer id);

    // WHERE email IN (?, ?, ?) AND birth < ?
    List<Person> getByEmailInAndBirthLessThan(List<String> emails, Date birth);

    // WHERE a.id > ?
    List<Person> getByAddress_IdGreaterThan(Integer id);

    // 查询 id 值最大的那个 Person
    @Query("SELECT p FROM Person p WHERE p.id = (SELECT max(p2.id) FROM Person p2)")
    Person getMaxIdPerson();

    @Query("SELECT p FROM Person p WHERE p.lastName = ?1 AND p.email = ?2")
    List<Person> testQueryAnnotationParams1(String lastName, String email);

    @Query("SELECT p FROM Person p WHERE p.lastName = :lastName AND p.email = :email")
    List<Person> testQueryAnnotationParams2(@Param("email") String email, @Param("lastName") String lastName);

    @Query("SELECT p FROM Person p WHERE p.lastName LIKE %?1% OR p.email LIKE %?2%")
    List<Person> testQueryAnnotationLikeParam(String lastName, String email);

    @Query("SELECT p FROM Person p WHERE p.lastName LIKE %:lastName% OR p.email LIKE %:email%")
    List<Person> testQueryAnnotationLikeParam2(@Param("email") String email, @Param("lastName") String lastName);

    @Query(value = "SELECT count(id) FROM jpa_persons", nativeQuery = true)
    long getTotalCount();

    @Modifying
    @Query("UPDATE Person p SET p.email = :email WHERE p.id = :id")
    void updatePersonEmail(@Param("id") Integer id, @Param("email") String email);


}
