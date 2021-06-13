package com.fastcampus.javaallinone.project2.mycontact.controller;

import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.repository.PersonRepository;
import com.fastcampus.javaallinone.project2.mycontact.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PersonControllerTest {
    @Autowired
    private PersonController personController;
    @Autowired
    private PersonRepository personRepository;
    private MockMvc mockMvc;

    //좋아하는 Refactoring~
    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void getPerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Justin")
                );
    }

    @Test
    public void postPerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\n" +
                                "    \"name\": \"Justin2\",\n" +
                                "    \"age\": 20,\n" +
                                "    \"bloodType\": \"B\",\n" +
                                "    \"year_of_birthday\": 1995,\n" +
                                "    \"month_of_birthday\": 2,\n" +
                                "    \"day_of_birthday\": 2\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isCreated()
                        //.andExpect(jsonPath("$.name").value("Justin")
                );
    }

    @Test
    public void putPerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\n" +
                                "    \"name\": \"Justin2\",\n" +
                                "    \"age\": 20,\n" +
                                "    \"bloodType\": \"B\",\n" +
                                "    \"year_of_birthday\": 1995,\n" +
                                "    \"month_of_birthday\": 2,\n" +
                                "    \"day_of_birthday\": 2,\n" +
                                "    \"job\": \"Unemployed\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk());
        List<Person> people = personRepository.findAll();
        people.forEach(System.out::println);
    }

    @Test
    public void patchPerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                        .param("name", "JustinPatch"))
                .andDo(print())
                .andExpect(status().isOk());
        List<Person> people = personRepository.findAll();
        people.forEach(System.out::println);
    }

    @Test
    public void deletePerson() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
        List<Person> people = personRepository.findPeopleDeleted();
        people.forEach(System.out::println);
    }

}