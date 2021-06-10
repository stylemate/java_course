package com.fastcampus.javaallinone.project2.mycontact.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Block {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String reason;
    private LocalDate StartDate;
    private LocalDate EndDate;
}
