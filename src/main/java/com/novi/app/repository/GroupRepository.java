package com.novi.app.repository;

import com.novi.app.model.Group;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
    void deleteById(@NotNull Long id);
}