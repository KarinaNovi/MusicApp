package com.novi.app.service;

import com.novi.app.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();
    void saveUser(User user);
    Optional<User> findUserById(Long userId);
    void updateUser(Long userId, User user);
    void deleteUser(Long userId);
    void terminateUser(User user);
    List<User> findNewlyCreatedUsers();
}
