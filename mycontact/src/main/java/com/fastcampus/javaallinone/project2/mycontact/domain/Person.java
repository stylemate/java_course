package com.fastcampus.javaallinone.project2.mycontact.domain;

import com.fastcampus.javaallinone.project2.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project2.mycontact.domain.dto.Birthday;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
//delete 상태 처리
@Where(clause ="deleted = false")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @NotEmpty
    @Column(nullable = false)
    private String name;
    @NonNull
    @Min(1)
    private int age;
    private String bloodType;
    private String address;
    @Valid
    @Embedded
    private Birthday birthday;
    private String job;
    private String phoneNumber;
    private String hobby;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    //설명이 조금...
    private Block block;

    @ColumnDefault("0")
    private boolean deleted;

    public void set(PersonDto personDto) {
        if (personDto.getAge() != 0)
            this.setAge(personDto.getAge());
        if(StringUtils.hasText(personDto.getHobby()))
            this.setHobby(personDto.getHobby());
        if(StringUtils.hasText(personDto.getBloodType()))
            this.setBloodType(personDto.getBloodType());
        if(StringUtils.hasText(personDto.getAddress()))
            this.setAddress(personDto.getAddress());
        if(StringUtils.hasText(personDto.getJob()))
            this.setJob(personDto.getJob());
        if(StringUtils.hasText(personDto.getPhoneNumber()))
            this.setPhoneNumber(personDto.getPhoneNumber());
    }
}
