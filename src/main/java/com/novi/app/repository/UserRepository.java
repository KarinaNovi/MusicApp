package com.novi.app.repository;

import com.novi.app.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    void deleteById(@NotNull Long id);
}