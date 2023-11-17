package com.novi.app.model.repository;

import com.novi.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
//default method - with implementation
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM USERS WHERE users.registration_date > :date", nativeQuery = true)
    List<User> findNewlyCreatedUsers(@Param("date") Date date);

    @Query(value = "SELECT * FROM USERS WHERE deletion_date = :date", nativeQuery = true)
    List<User> findActiveUsers(@Param("date") Date date);
}