package com.novi.app.model.repository;

import com.novi.app.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query(value = "SELECT * FROM LOCATIONS WHERE repetition_dtm >= current_date", nativeQuery = true)
    List<Location> findLocationsWithRepetitionToday();
}