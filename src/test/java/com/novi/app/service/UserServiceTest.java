package com.novi.app.service;

import com.novi.app.model.User;
import com.novi.app.util.Constants;
import com.novi.app.util.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {


    @Autowired
    private UserService userService;

    @Test
    @Rollback
    public void saveAndPhysicalDeleteTests() {
        String firstName = "Tom";
        String lastName = "Cat";
        String phoneNumber = "79103421050";
        String email = "1@gmail.com";
        String password = "12345";
        String birthday = String.valueOf(LocalDate.of(1997,9,21));
        User user = new User(firstName,lastName,null,phoneNumber,email,null,password,birthday);
        userService.saveUser(user);
        Optional<User> checkSuccessfulSave = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulSave.isPresent());
        userService.deleteUser(user.getUserId());
        Optional<User> checkSuccessfulDelete = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulDelete.isEmpty());
    }

    @Test
    @Rollback
    public void testNewlyCreatedUsers() {
        // TODO: create logger for such tests
        System.out.println(userService.findNewlyCreatedUsers());
    }

    @Test
    @Rollback
    public void testTerminationUser() {
        String firstName = "Tom";
        String lastName = "Cat";
        String phoneNumber = "79103421050";
        String email = "1@gmail.com";
        String password = "12345";
        String birthday = String.valueOf(LocalDate.of(1997,9,21));
        User user = new User(firstName,lastName,null,phoneNumber,email,null,password,birthday);
        userService.saveUser(user);
        Optional<User> checkSuccessfulSave = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulSave.isPresent());
        userService.terminateUser(user);
        Optional<User> checkSuccessfulTermination = userService.findUserById(user.getUserId());
        if (checkSuccessfulTermination.isPresent()) {
            String deletionDate = checkSuccessfulTermination.get().getDeletionDtm();
            Assertions.assertNotEquals(deletionDate, UserUtil.formatDate(new Date(Constants.MAX_DATE)));
        }
        // clean up after test - temporary till test DB
        userService.deleteUser(user.getUserId());
        Optional<User> checkSuccessfulDelete = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulDelete.isEmpty());
    }
}
