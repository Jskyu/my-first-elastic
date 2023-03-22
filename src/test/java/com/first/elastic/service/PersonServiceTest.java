package com.first.elastic.service;

import com.first.elastic.document.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PersonServiceTest {

    private final PersonService service;

    @Autowired
    public PersonServiceTest(PersonService service) {
        this.service = service;
    }

    @Test public void saveTest(){
        //give
        Person person = new Person();
        person.setId("1");
        person.setName("Alex");

        //when
        Person savePerson = service.save(person);

        //then
        Assertions.assertEquals(savePerson, person);
    }

    @Test
    public void findByIdTest(){
        //give
        Person person = new Person();
        person.setId("1");
        person.setName("Alex");
        Person savePerson = service.save(person);

        try {
            //when
            Person findPerson = service.findById(savePerson.getId());

            //then
            Assertions.assertEquals(savePerson, findPerson);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllTest(){
        List<Person> list = service.findAll();

        System.out.println("list = " + list);
    }
}
