package com.novi.app.service.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.novi.app.model.User;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.empty", "Cannot be empty!");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "lastName.empty", "Cannot be empty!");
        ValidationUtils.rejectIfEmpty(errors, "phoneNumber", "phoneNumber.empty", "Cannot be empty!");
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty", "Cannot be empty!");
    }
}