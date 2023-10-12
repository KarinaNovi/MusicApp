package com.novi.app.service;

import com.novi.app.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class UserServiceTest {


    @Autowired
    private UserService userService;

    @Test
    @Rollback
    public void saveAndDeleteTests() {
        String firstName = "Tom";
        String lastName = "Cat";
        String phoneNumber = "79103421050";
        String email = "1@gmail.com";
        String login = String.valueOf(UUID.randomUUID());
        String password = "12345";
        String birthday = String.valueOf(LocalDate.of(1997,9,21));
        User user = new User(firstName,lastName,null,phoneNumber,email,login,password,birthday);
        userService.save(user);
        Optional<User> checkSuccessfulSave = userService.findById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulSave.isPresent());
        userService.delete(user.getUserId());
        Optional<User> checkSuccessfulDelete = userService.findById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulDelete.isEmpty());
    }

    @Test
    @Rollback
    public void testNewlyCreatedUsers() {
        System.out.println(userService.findNewlyCreatedUsers());
    }
}
