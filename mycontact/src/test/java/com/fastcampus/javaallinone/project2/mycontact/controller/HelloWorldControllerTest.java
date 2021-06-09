package com.fastcampus.javaallinone.project2.mycontact.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

@SpringBootTest
class HelloWorldControllerTest {
    @Autowired
    private HelloWorldController helloWorldController;

    private MockMvc mockMvc;

    @Test
    void helloWorld() {
        //System.out.println(helloWorldController.helloWorld());

        assertThat(helloWorldController.helloWorld(), is(equalTo("Hello World!")));
    }

    @Test
    void mockMvcTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloWorld")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string("Hello World!"));
    }
}