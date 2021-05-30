package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
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
        String account = "BuilderTest";
        String password = "Test03";
        UserStatus status = UserStatus.REGISTERED;
        String email = "test@example.com";
        String phoneNumber = "010-1234-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

//        User user = new User();
//        user.setAccount(account);
//        user.setPassword(password);
//        user.setStatus(status);
//        user.setEmail(email);
//        user.setPhoneNumber(phoneNumber);
//        user.setRegisteredAt(registeredAt);
////        user.setCreatedAt(createdAt);
////        user.setCreatedBy(createdBy);

        User u = User.builder()
                .account(account)
                .password(password)
                .status(status)
                .email(email)
                .phoneNumber(phoneNumber)
                .registeredAt(registeredAt)
                .build();

        User newUser = userRepository.save(u);

        Assertions.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read() {
        Optional<User> user = userRepository.findById(2L);
        Assertions.assertNotNull(user);

        user.get().getOrderGroupList().stream().forEach(orderGroup -> {
            System.out.println("Recipient: " + orderGroup.getRevName());
            System.out.println("order detail");

            orderGroup.getOrderDetailList().forEach(orderDetail -> {
                System.out.println("order status: " + orderDetail.getStatus());
                System.out.println("order item: " + orderDetail.getItem().getName());
                System.out.println("order partner: " + orderDetail.getItem().getPartner().getName());
                System.out.println("order category: " + orderDetail.getItem().getPartner().getCategory().getTitle());
            });
        });
    }

    @Test
    public void update() {
        Optional<User> user = userRepository.findById(1L);

//        //Chaining
//        user.get().setEmail("")
//                .setPhoneNumber("")
//                .setStatus("");

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
