package com.novi.app.service.impl;

import com.novi.app.service.UserService;
import com.novi.app.util.UserUtil;
import org.springframework.stereotype.Service;
import com.novi.app.model.User;
import com.novi.app.model.repository.UserRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    //TODO: add logger

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void update(Long userId, User user) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            userRepository.save(optionalUser.get());
        } else {
            System.out.println("No existing user with such id");
        }
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> findNewlyCreatedUsers() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.MONTH, -1);
        String formattedDate = UserUtil.formatDate(currentDate.getTime());
        return userRepository.findNewlyCreatedUsers(formattedDate);
    }

}
