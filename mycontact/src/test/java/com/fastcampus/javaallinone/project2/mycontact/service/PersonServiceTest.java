package com.fastcampus.javaallinone.project2.mycontact.service;

import com.fastcampus.javaallinone.project2.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.project2.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;

    @Test
    void getPeopleByName() {
        //separation of concern. repo 오류는 repo 에서 테스트하는 unit test 방식
        when(personRepository.findByName("Justin"))
                .thenReturn(Lists.newArrayList(new Person("Justin")));
        List<Person> result = personService.getPeopleByName("Justin");

        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.get(0).getName(), "Justin");
    }

    @Test
    void getPerson() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("Justin")));

        Person person = personService.getPerson(1L);
        Assertions.assertEquals(person.getName(), "Justin");

    }

    @Test
    void getPersonIfNotFound() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        Person person = personService.getPerson(1L);
        Assertions.assertNull(person);

    }
}