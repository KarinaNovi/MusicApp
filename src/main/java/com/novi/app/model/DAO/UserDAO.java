package com.novi.app.model.DAO;

import com.novi.app.model.User;

import java.util.List;

public interface UserDAO{

    User findById(int userId);

    void save(User user);

    void update(int userId, User updateUser);

    void deleteById(int userId);

    List<User> findAll();

}