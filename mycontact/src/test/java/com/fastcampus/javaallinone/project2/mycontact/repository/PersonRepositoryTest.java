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
    void crud() {
        Person person = new Person();//.builder().name("Justin").age(10).bloodType("A").birthday(new Birthday(LocalDate.of(1993, 1, 1))).build();
        person.setName("Justin");
        person.setAge(29);
        person.setBloodType("B");

        personRepository.save(person);

        System.out.println(personRepository.findAll());

        Optional<Person> foundPerson = personRepository.findById(2L);

        Assertions.assertEquals(foundPerson.get().getName(), "Justin");
        Assertions.assertEquals(foundPerson.get().getAge(), 29);
        Assertions.assertEquals(foundPerson.get().getBloodType(), "B");
    }

    @Test
    void findByBloodType() {
        givenPerson("Justin", 10, "A");
        givenPerson("David", 10, "B");
        givenPerson("Dennis", 10, "O");
        givenPerson("Justin", 10, "AB");
        givenPerson("Justin", 10, "A");

        List<Person> result = personRepository.findByBloodType("A");

        result.forEach(System.out::println);
    }

    @Test
    void findByBirthdayBetween() {
        givenPerson("Justin", 10, "A", LocalDate.of(1990, 5, 1));
        givenPerson("David", 10, "B", LocalDate.of(1990, 4, 1));
        givenPerson("Dennis", 10, "O", LocalDate.of(1990, 3, 1));
        givenPerson("Justin", 10, "AB", LocalDate.of(1990, 2, 1));
        givenPerson("Justin", 10, "A", LocalDate.of(1993, 1, 1));

        List<Person> result = personRepository.findByMonthOfBirthday(1, 1);

        result.forEach(System.out::println);
    }

    //method overloading
    private void givenPerson(String name, int age, String bloodType) {
        givenPerson(name, age, bloodType, null);
    }

    private void givenPerson(String name, int age, String bloodType, LocalDate birthday) {
        personRepository.save(Person.builder().name(name).age(age).bloodType(bloodType).birthday(new Birthday(birthday)).build());
    }
}