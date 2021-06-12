package com.fastcampus.javaallinone.project2.mycontact.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@Data
public class Birthday {
    private int yearOfBirthday;
    @Min(1)
    @Max(12)
    private int monthOfBirthday;
    @Min(1)
    @Max(31)
    private int dayOfBirthday;

    public Birthday(LocalDate birthday) {
        //LocalDate의 메소드가 자동으로 validation 해줌
        this.yearOfBirthday = birthday.getYear();
        this.monthOfBirthday = birthday.getMonthValue();
        this.dayOfBirthday = birthday.getDayOfMonth();
    }
}
