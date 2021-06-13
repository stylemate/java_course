package com.fastcampus.javaallinone.project2.mycontact.repository;

import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.domain.dto.Birthday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByName(String name);

    //jpql, what redundancy?
    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = :monthOfBirthday")
    List<Person> findByMonthOfBirthday(@Param("monthOfBirthday") int monthOfBirthday);

    @Query(value = "select * from Person person where person.deleted = true", nativeQuery = true)
    List<Person> findPeopleDeleted();

    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = :monthOfBirthdayToday and person.birthday.dayOfBirthday = :dayOfBirthdayToday or person.birthday.monthOfBirthday = :monthOfBirthdayNextDay and person.birthday.dayOfBirthday = :dayOfBirthdayNextDay", nativeQuery = false)
    List<Person> findBirthdayFriends(@Param("monthOfBirthdayToday") int monthOfBirthdayToday, @Param("dayOfBirthdayToday") int dayOfBirthdayToday, @Param("monthOfBirthdayNextDay") int monthOfBirthdayNextDay, @Param("dayOfBirthdayNextDay") int dayOfBirthdayNextDay);

//    List<Person> findByBirthdayBetween(Birthday fromBirthday, Birthday toBirthday);
//    List<Person> findByBirthday_MonthOfBirthdayAndBirthday_DayOfBirthday(int month, int day);
//    List<Person> findByBirthday_MonthOfBirthday(int month);
}
