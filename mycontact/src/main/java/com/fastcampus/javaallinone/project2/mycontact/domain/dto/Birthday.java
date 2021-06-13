package com.fastcampus.javaallinone.project2.mycontact.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@Data
public class Birthday {
    private Integer yearOfBirthday;

    private Integer monthOfBirthday;

    private Integer dayOfBirthday;

    private Birthday(LocalDate birthday) {
        //LocalDate의 메소드가 자동으로 validation 해줌
        this.yearOfBirthday = birthday.getYear();
        this.monthOfBirthday = birthday.getMonthValue();
        this.dayOfBirthday = birthday.getDayOfMonth();
    }

    public int getAge() {
        return LocalDate.now().getYear() - this.yearOfBirthday + 1;
    }

    public boolean isBirthdayToday() {
        return LocalDate.now().equals(LocalDate.of(yearOfBirthday, monthOfBirthday, dayOfBirthday));
    }

    public static Birthday of(LocalDate birthday) {
        return new Birthday(birthday);
    }
}
