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
import java.time.LocalDate;


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

    private String address;
    @Valid
    @Embedded
    private Birthday birthday;
    private String job;
    private String phoneNumber;
    private String hobby;

    @ColumnDefault("0")
    private boolean deleted;

    public Person(String name) {
        this.setName(name);
    }

    public void set(PersonDto personDto) {
        if(StringUtils.hasText(personDto.getHobby()))
            this.setHobby(personDto.getHobby());
        if(StringUtils.hasText(personDto.getAddress()))
            this.setAddress(personDto.getAddress());
        if(StringUtils.hasText(personDto.getJob()))
            this.setJob(personDto.getJob());
        if(StringUtils.hasText(personDto.getPhoneNumber()))
            this.setPhoneNumber(personDto.getPhoneNumber());
        if((personDto.getBirthday()) != null)
            this.setBirthday(Birthday.of(personDto.getBirthday()));
    }

    public Integer getAge() {
        if(this.birthday != null)
            return LocalDate.now().getYear() - this.birthday.getYearOfBirthday() - 1;
        else
            return null;
    }

    public boolean isBirthdayToday() {
        return LocalDate.now().equals(LocalDate.of(this.birthday.getYearOfBirthday(), this.birthday.getMonthOfBirthday(), this.birthday.getDayOfBirthday()));
    }
}
