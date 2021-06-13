package com.fastcampus.javaallinone.project2.mycontact.controller;

import com.fastcampus.javaallinone.project2.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.repository.PersonRepository;
import com.fastcampus.javaallinone.project2.mycontact.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/person")
@RestController
@Slf4j
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postPerson(@RequestBody Person person) {
        personService.postPerson(person);
        log.info("person -> {}", personRepository.findAll());
    }

    @PutMapping("/{id}")
    public void putPerson(@PathVariable Long id, @RequestBody PersonDto personDto) {
        personService.putPerson(id, personDto);
        log.info("person -> {}", personRepository.findAll());
    }

    @PatchMapping("/{id}")
    public void patchPerson(@PathVariable Long id, String name) {
        //강의는 메소드 오버로딩을 사용하지만 그냥 네이밍이 맘에 안들어서 분리
        personService.patchPerson(id, name);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }
}
