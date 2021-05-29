package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.AdminUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class AdminUserRepositoryTest extends StudyApplicationTests {

    @Autowired
    //Dependency Injection 직접 개체 생성 불필요
    private AdminUserRepository adminUserRepository;

    @Test
    public void create() {
        String account = "Test02";
        String password = "Test02";
        String status = "REGISTERED";
        String role = "ADMIN";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        AdminUser adminUser = new AdminUser();
        adminUser.setAccount(account);
        adminUser.setPassword(password);
        adminUser.setStatus(status);
        adminUser.setRole(role);
        adminUser.setRegisteredAt(registeredAt);
//        user.setCreatedAt(createdAt);
//        user.setCreatedBy(createdBy);

        AdminUser newAdminUser = adminUserRepository.save(adminUser);

        Assertions.assertNotNull(newAdminUser);
    }
}
