package com.fastcampus.javaallinone.project2.mycontact.service;

import com.fastcampus.javaallinone.project2.mycontact.domain.Block;
import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.repository.BlockRepository;
import com.fastcampus.javaallinone.project2.mycontact.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    @Test
    void getPeopleExcludeBlocks() {
        givenPeople();

        List<Person> result = personService.getPeopleExcludeBlocks();

        result.forEach(System.out::println);
    }

    @Test
    void getPeopleByName() {
        givenPeople();

        List<Person> result = personService.getPeopleByName("Justin");

        result.forEach(System.out::println);
    }

    @Test
    void cascadeTest() {
        givenPeople();

        List<Person> result = personRepository.findAll();

        result.forEach(System.out::println);

        Person person = result.get(3);
        person.getBlock().setStartDate(LocalDate.now());
        person.getBlock().setEndDate(LocalDate.now());


        //merge
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);

        //remove
//        personRepository.delete(person);
//        personRepository.findAll().forEach(System.out::println);
//        blockRepository.findAll().forEach(System.out::println);
        person.setBlock(null);
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);
        //orphan removal
        blockRepository.findAll().forEach(System.out::println);
    }

    @Test
    void getPerson() {
        givenPeople();

        Person person = personService.getPerson(4L);
    }

    private void givenPeople() {
        givenPerson("Justin", 29, "B");
        givenPerson("David", 25, "A");
        givenPerson("Dennis", 5, "O");
        givenBlockPerson("Justin", 28, "AB");
    }

    private void givenPerson(String name, int age, String bloodType) {
        personRepository.save(Person.builder().name(name).age(age).bloodType(bloodType).build());
    }

    private void givenBlockPerson(String name, int age, String bloodType) {
        Person blockPerson = Person.builder().name(name).age(age).bloodType(bloodType).build();
        blockPerson.setBlock(Block.builder().name(name).build());
        personRepository.save(blockPerson);
    }

}