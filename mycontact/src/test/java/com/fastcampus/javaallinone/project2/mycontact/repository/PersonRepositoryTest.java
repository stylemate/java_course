package com.fastcampus.javaallinone.project2.mycontact.repository;

import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud() {
        Person person = new Person();
        person.setName("Justin");
        person.setAge(29);
        person.setBloodType("B");

        personRepository.save(person);

        System.out.println(personRepository.findAll());

        Optional<Person> foundPerson = personRepository.findById(1L);

        Assertions.assertEquals(foundPerson.get().getName(), "Justin");
        Assertions.assertEquals(foundPerson.get().getAge(), 29);
        Assertions.assertEquals(foundPerson.get().getBloodType(), "B");
    }

}