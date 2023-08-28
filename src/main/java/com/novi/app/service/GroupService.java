package com.novi.app.service;

import com.novi.app.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<Group> findAll();
    void save(Group group);
    Optional<Group> findById(Long id);
    void delete(Long id);

}
