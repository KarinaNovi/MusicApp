package com.novi.app.model.repository;

import com.novi.app.model.Group;
import com.novi.app.model.MusicStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query
    List<Group> getGroupsByMusicStyles(@Param("musicStyle") MusicStyle musicStyle);
}