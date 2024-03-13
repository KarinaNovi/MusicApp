package com.novi.app.model.repository;

import com.novi.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
//default method - with implementation
// PagingAndSortingRepository - to work with Pages - better with large result sets
public interface UserRepository extends JpaRepository<User, Long> {

    // better to use JPA method naming insteadof SQL queries in @Query
    // so can be used in many DB types
    @Query(value = "SELECT * FROM USERS WHERE users.registration_date > :date", nativeQuery = true)
    List<User> findNewlyCreatedUsers(@Param("date") Date date);

    @Query(value = "SELECT * FROM USERS WHERE deletion_date = :date", nativeQuery = true)
    List<User> findActiveUsers(@Param("date") Date date);

    Optional<User> findByUserLogin(String userLogin);
}