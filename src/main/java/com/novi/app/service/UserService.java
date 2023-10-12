package com.novi.app.service;

import com.novi.app.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    void save(User user);
    Optional<User> findById(Long userId);
    void update(Long userId, User user);
    void delete(Long userId);
    List<User> findNewlyCreatedUsers();
}
