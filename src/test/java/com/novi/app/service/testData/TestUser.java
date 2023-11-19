package com.novi.app.service.testData;

import com.novi.app.model.User;

import java.time.LocalDate;

public final class TestUser {

    public static User createSimpleUser() {
        String firstName = "Mock";
        String lastName = "Results";
        String phoneNumber = "79103421050";
        String email = "1@gmail.com";
        String password = "12345";
        String birthday = String.valueOf(LocalDate.of(1997,9,21));
        return new User(firstName,lastName,null,phoneNumber,email,null,password,birthday);
    }

    public static User updateSimpleUser(long userId) {
        User user = createSimpleUser();
        user.setUserLogin("Some custom login");
        user.setUserId(userId);
        return user;
    }
}
