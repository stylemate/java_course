package com.fastcampus.javaallinone.project2.mycontact.controller;

import com.fastcampus.javaallinone.project2.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.repository.PersonRepository;
import com.fastcampus.javaallinone.project2.mycontact.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        //난잡하네
//        return personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(id));
    }

    @GetMapping("/birthday-friends")
    public List<Person> getBirthdayFriends() {
        /*
        시간은 충분하다고 생각했지만 예상치 못한 변수(deprecated 된 메소드들, h2 설정 등등)들이 너무 많아 강의 Test 부분은 많이 반영시키지 못했습니다.
        personService 내 getBirthdayFriends 라는 메소드를 실행시킵니다.
        getBirthdayFriends 메소드는 PersonRepository에 Query Method를 활용하여 만든 sql문을 실행시켜 Person 리스트를 반환합니다.
        sql문은 더 elegant 하게 만드는 방법들이 존재하는것을 알지만 DATE_ADD 와 같은 함수와 INTERVAL 을 쓰려면 Birthday 를 뜯어고칠 엄두가 나지 않아 무식하게 query 했습니다.
        Test는 PersonControllerTest 내 getBirthdayFriends()로 확인하거나, 직접 Application을 실행시켜 http://localhost:8080/api/person/birthday-friends 로 GET request 를 보내면 확인할 수 있습니다.
         */
        return personService.getBirthdayFriends();
    }
}
