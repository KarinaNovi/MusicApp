package com.novi.app.service.testData;

import com.novi.app.model.User;

import java.time.LocalDate;

public final class TestUser {

    public static User createSimpleUser() {
        String firstName = "Tom";
        String lastName = "Cat";
        String phoneNumber = "79103421050";
        String email = "1@gmail.com";
        String password = "12345";
        String birthday = String.valueOf(LocalDate.of(1997,9,21));
        return new User(firstName,lastName,null,phoneNumber,email,null,password,birthday);
    }
}
