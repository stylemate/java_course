package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Test
    public void read() {
        Optional<User> user = userRepository.findById(1L);

        user.ifPresent(foundUser -> {
            System.out.println("user: " + foundUser);
        });
    }

    @Test
    public void update() {
        Optional<User> user = userRepository.findById(1L);

        user.ifPresent(foundUser -> {
            foundUser.setAccount("UPDATED");
            foundUser.setUpdateAt(LocalDateTime.now());
            foundUser.setUpdatedBy("me");

            //JPA automatically determines creation or update by "ID"
            userRepository.save(foundUser);
        });
    }

    public void delete() {

    }
}
