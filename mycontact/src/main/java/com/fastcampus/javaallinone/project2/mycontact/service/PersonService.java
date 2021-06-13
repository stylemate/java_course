package com.fastcampus.javaallinone.project2.mycontact.service;

import com.fastcampus.javaallinone.project2.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.project2.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Transactional(readOnly = true)
    public Person getPerson(Long id) {
//        Optional<Person> person = personRepository.findById(id);
//
//        //log.info("person: {}", person.get());
//        if (person.isPresent())
//            return person.get();
//        else
//            return null;
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void postPerson(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void putPerson(Long id, PersonDto personDto) {
        Person foundPerson = personRepository.findById(id).orElseThrow(() -> new RuntimeException("PERSON DOESN'T EXIST"));

        // 그냥 시스템적으로 이름 변경은 막고 싶었던거군
        if (!personDto.getName().equals(foundPerson.getName()))
            throw new RuntimeException("Name mismatch");
//        foundPerson.setName(personDto.getName());
//        foundPerson.setPhoneNumber(personDto.getPhoneNumber());
//        foundPerson.setJob(personDto.getJob());
//        foundPerson.setAge(personDto.getAge());
//        foundPerson.setBloodType(personDto.getBloodType());
//        if (personDto.getBirthday() != null)
//            foundPerson.setBirthday(new Birthday(personDto.getBirthday()));
//        foundPerson.setAddress(personDto.getAddress());
//        foundPerson.setHobby(personDto.getHobby());
//
//        System.out.println("여길 봐봐" + foundPerson);
        foundPerson.set(personDto);

        personRepository.save(foundPerson);
    }

    public void patchPerson(Long id, String name) {
        Person foundPerson = personRepository.findById(id).orElseThrow(() -> new RuntimeException("PERSON DOESN'T EXIST"));
        foundPerson.setName(name);
        personRepository.save(foundPerson);
    }

    public void deletePerson(Long id) {
//        personRepository.deleteById(id);
        // instead of deleting a record in the db, just mark it deleted
        Person foundPerson = personRepository.findById(id).orElseThrow(()-> new RuntimeException("PERSON DOESN'T EXIST"));

        foundPerson.setDeleted(true);

        personRepository.save(foundPerson);
    }

    public List<Person> getPeopleByName(String name) {
//        List<Person> people = personRepository.findAll();
//
//        return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());
        return personRepository.findByName(name);
    }


    public List<Person> getBirthdayFriends() {
//        return personRepository.findByBirthday_MonthOfBirthdayAndBirthday_DayOfBirthday(LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
//        List<Person> people = personRepository.findByMonthOfBirthday(6);
//        people.forEach(System.out::println);
        return personRepository.findBirthdayFriends(LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth(), LocalDate.now().plusDays(1).getMonthValue(), LocalDate.now().plusDays(1).getDayOfMonth());
    }
}
