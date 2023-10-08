package com.novi.app.model.repository;

import com.novi.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//default method - with implementation
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM USERS WHERE registration_dtm =< :date", nativeQuery = true)
    List<User> newlyCreatedUsers(@Param("date") Long date);

//    @Query(value = "SELECT * FROM USERS WHERE user_id = ? and TO_DATE(BIRTHDAY, 'DD.MM.YYYY') < TO_DATE('01.01.1990', 'DD.MM.YYYY')", nativeQuery = true)
//    List<User> findAdult(int userId);
}