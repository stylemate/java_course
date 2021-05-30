package com.example.study.model.enumclass;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserStatus {
    REGISTERED(0, "REGISTERED", "User registered"),
    UNREGISTERED(1, "UNREGISTERED", "User unregistered");

    private Integer Id;
    private String title;
    private String description;
}
