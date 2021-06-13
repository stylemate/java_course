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
    //수명? 싸이클을 몰라서 그러는데 이렇게 하면 객체가 생성될 때 ? 컴파일 될 때? 값이 정해져서 나오지 않나?
    // 11:59분에 값이 정해진 후 자정에 쓰면 오류 아닐까
    private static LocalDate today = LocalDate.now();

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
        return today.getYear() - this.yearOfBirthday + 1;
    }

    public boolean isBirthdayToday() {
        return today.equals(LocalDate.of(yearOfBirthday, monthOfBirthday, dayOfBirthday));
    }

    public static Birthday of(LocalDate birthday) {
        return new Birthday(birthday);
    }
}
