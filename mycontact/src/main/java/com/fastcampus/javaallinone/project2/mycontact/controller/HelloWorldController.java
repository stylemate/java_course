package com.fastcampus.javaallinone.project2.mycontact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping(value = "api/helloWorld")
    public String helloWorld() {
        return "Hello World!";
    }
}
