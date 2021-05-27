package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired
    //Dependency Injection 직접 개체 생성 불필요
    private UserRepository userRepository;

    @Test
    public void create() {
        User user = new User();
        //user.setId(); auto increment
        user.setAccount("aerg");
        user.setEmail("test@example.com");
        user.setPhoneNumber("010-1234-5678");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("aerg");

        User newUser = userRepository.save(user);
        //lombok의 toString 만 살려둔다...
        System.out.println("Test: " + newUser);
    }

    public void read() {

    }

    public void update() {

    }

    public void delete() {

    }
}
