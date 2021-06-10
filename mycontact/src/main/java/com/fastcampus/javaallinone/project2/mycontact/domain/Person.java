package com.fastcampus.javaallinone.project2.mycontact.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;
    private String bloodType;
    private String address;
    private LocalDate birthday;
    private String job;
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = true)
    //설명이 조금...
    private Block block;
}
