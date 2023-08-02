package com.novi.app.controller;

import com.novi.app.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class UserControllerTest {

    @Test
    public void createUser() {
        String firstName = "Tom";
        String lastName = "Cat";
        String phoneNumber = "79103421050";
        String email = "1@gmail.com";
        String login = String.valueOf(UUID.randomUUID());
        String password = "12345";
        String birthday = String.valueOf(LocalDate.of(1997,9,21));
        User user = new User(firstName,lastName,null,phoneNumber,email,login,password,birthday);
        System.out.println(user);
    }
}
