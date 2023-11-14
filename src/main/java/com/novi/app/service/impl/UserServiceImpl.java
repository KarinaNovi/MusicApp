package com.novi.app.service.impl;

import com.novi.app.service.UserService;
import com.novi.app.util.Constants;
import org.springframework.stereotype.Service;
import com.novi.app.model.User;
import com.novi.app.model.repository.UserRepository;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    //TODO: add logger

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(Long userId, User user) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            userRepository.save(user);
        } else {
            System.out.println("WARN: No existing user with such id");
        }
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void terminateUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();
            userToUpdate.setDeletionDtm(new Date());
            userRepository.save(userToUpdate);
        } else {
            System.out.println("WARN: No existing user with such id");
        }
    }

    @Override
    public List<User> findNewlyCreatedUsers() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.MONTH, -1);
        return userRepository.findNewlyCreatedUsers(currentDate.getTime());
    }

    @Override
    public List<User> findActiveUsers() {
        return userRepository.findActiveUsers(new Date(Constants.MAX_DATE));
    }
}