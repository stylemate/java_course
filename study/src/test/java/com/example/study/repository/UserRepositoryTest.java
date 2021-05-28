package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
        user.setAccount("Erasmus");
        user.setPassword("password");
        user.setStatus("Active");
        user.setEmail("test@example.com");
        user.setPhoneNumber("010-1234-5678");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("me");

        User newUser = userRepository.save(user);
        System.out.println("Test: " + newUser);
    }

    @Test
    @Transactional
    public void read() {
        Optional<User> user = userRepository.findById(1L);

        user.ifPresent(foundUser -> {
            foundUser.getOrderDetailList().stream().forEach(detail -> {
                Item item = detail.getItem(); //getItem() method will return Item Object!
                System.out.println("status: " + detail.getStatus() + "\nitem id: " + item.getId() + "\nitem name: " + item.getName());
            });
        });
    }

    @Test
    public void update() {
        Optional<User> user = userRepository.findById(1L);

        user.ifPresent(foundUser -> {
            foundUser.setAccount("UPDATED");
            foundUser.setUpdatedAt(LocalDateTime.now());
            foundUser.setUpdatedBy("me");

            //JPA automatically determines creation or update by "ID"
            userRepository.save(foundUser);
        });
    }

    @Test
    @Transactional
    //rollback
    public void delete() {
        Optional<User> user = userRepository.findById(2L);

        Assertions.assertTrue(user.isPresent());

        user.ifPresent(foundUser -> {
            userRepository.delete(foundUser); //return type void
        });

        Optional<User> deletedUser = userRepository.findById(2L);

        Assertions.assertFalse(deletedUser.isPresent());

        if (deletedUser.isPresent())
            System.out.println("Why are you alive? " + deletedUser.get());
        else
            System.out.println("Deleted");
    }
}
