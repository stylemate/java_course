package com.example.study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor

@Entity
//@Table(name="user") //redundant
//the name of the class should match db table name
public class User {
    //@Column(name="id") //redundant if they match
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //강의가 이게 뭔지 별 설명은 안해줌
    //대충 react의 mongoose 같은 느낌
    private Long id;
    private String account;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updateAt;
    private String updatedBy;
}
