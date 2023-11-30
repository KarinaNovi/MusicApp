package com.novi.app.model.repository;

import com.novi.app.model.MusicInstrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicInstrumentRepository extends JpaRepository<MusicInstrument, Integer> {
}