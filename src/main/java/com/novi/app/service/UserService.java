package com.novi.app.service;

import com.novi.app.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // single user operation
    Optional<User> findUserById(Long userId);
    void saveUser(User user);
    void updateUser(Long userId, User user);
    void deleteUser(Long userId);
    void terminateUser(Long userId);

    // Many users operation
    List<User> findAllUsers();
    List<User> findNewlyCreatedUsers();
    List<User> findActiveUsers();
}