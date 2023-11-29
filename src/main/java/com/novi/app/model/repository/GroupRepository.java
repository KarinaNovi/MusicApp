package com.novi.app.model.repository;

import com.novi.app.model.Group;
import com.novi.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query(value = "SELECT * FROM GROUPS WHERE registration_date > :date", nativeQuery = true)
    List<Group> findNewlyCreatedGroups(@Param("date") Date date);

    @Query(value = "SELECT * FROM GROUPS WHERE deletion_date = :date", nativeQuery = true)
    List<Group> findActiveGroups(@Param("date") Date date);
}