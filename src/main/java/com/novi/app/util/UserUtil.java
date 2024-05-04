package com.novi.app.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public final class UserUtil {

    public static String formatName(String inputName) {
        return inputName == null || inputName.isEmpty()
                ? inputName
                : inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
    }

    public static String formatPhoneNumber(String phoneNumber) {
        return phoneNumber.substring(0, 1).replaceAll("\\d", "+7") + " (" + phoneNumber.substring(1, 4) + ") " + phoneNumber.substring(4, 7) + " " + phoneNumber.substring(7, 9) + " " + phoneNumber.substring(9);
    }

    public static String formatUserLogin(String userLogin) {
        return !StringUtils.isEmpty(userLogin) ? userLogin : String.valueOf(UUID.randomUUID());
    }

    // TODO: create specific class for encryption and de- process
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
