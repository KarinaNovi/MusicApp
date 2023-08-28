package com.novi.app.model.repository;

import com.novi.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM USERS WHERE TO_DATE(BIRTHDAY, 'DD.MM.YYYY') < TO_DATE('01.01.1990', 'DD.MM.YYYY')", nativeQuery = true)
    List<User> findAdult();
}