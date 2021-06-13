package com.fastcampus.javaallinone.project2.mycontact.repository;

import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void findByName() {
        List<Person> people = personRepository.findByName("Tony");
        Assertions.assertEquals(people.size(), 1);

        Person person = people.get(0);
        assertAll(
                () -> assertEquals(person.getName(), "Tony"),
                () -> assertEquals(person.getHobby(), "Reading"),
                () -> assertEquals(person.getAddress(), "Seoul"),
                () -> assertEquals(person.getBirthday(), Birthday.of(LocalDate.of(1991, 7, 10))),
                () -> assertEquals(person.getJob(), "Officer"),
                () -> assertEquals(person.getPhoneNumber(), "010-1234-5678"),
                () -> assertEquals(person.isDeleted(), false)
        );
    }

    @Test
    void findByNameIfDeleted() {
        List<Person> people = personRepository.findByName("Andrew");
        assertEquals(people.size(), 0);
    }

    @Test
    void findByMonthOfBirthday() {
        List<Person> people = personRepository.findByMonthOfBirthday(7);
        assertEquals(people.size(), 2);
        assertAll(
                () -> assertEquals(people.get(0).getName(), "David"),
                () -> assertEquals(people.get(1).getName(), "Tony")
        );
    }

    @Test
    void findPeopleDeleted() {
        List<Person> people = personRepository.findPeopleDeleted();

        assertEquals(people.size(), 1);
        assertEquals(people.get(0).getName(), "Andrew");
    }
}