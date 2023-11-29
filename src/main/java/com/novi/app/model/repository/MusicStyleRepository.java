package com.novi.app.model.repository;

import com.novi.app.model.MusicStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicStyleRepository extends JpaRepository<MusicStyle, Integer> {
}