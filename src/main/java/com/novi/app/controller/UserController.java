package com.novi.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.novi.app.model.User;
import com.novi.app.service.UserService;
import com.novi.app.service.validator.UserValidator;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    // тут вызывается пока еще пустая таблица
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUser(Model model) {
        model.addAttribute("addUser", new User());
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    // тут вставляются свои данные в инпуты с учетом валидации
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String getUser( @ModelAttribute("user") User user, BindingResult bindingResult,
                           Model model) {
        model.addAttribute("addUser", new User());
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String middleName = user.getMiddleName();
        String phoneNumber = user.getPhoneNumber();
        String email = user.getEmail();
        String password = user.getMd5();
        String birthday = user.getBirthday();
        long registrationDate = System.currentTimeMillis();
        // TODO: add automatic service to add max default value
        long deletedDate = 1L;
        // TODO: if user doesn't set it - generate random value based on name and b-day
        String login = user.getUserLogin();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users";
        }
        user = new User(firstName.substring(0, 1).toUpperCase() + firstName.substring(1),
                lastName.substring(0, 1).toUpperCase() + lastName.substring(1),
                phoneNumber.charAt(0) + " (" + phoneNumber.substring(1, 4) + ") " + phoneNumber.substring(4, 7) + " " + phoneNumber.substring(7, 9) + " " + phoneNumber.substring(9), email);
        userService.save(user);
        return "redirect:/users";
    }

    // тут информация из строки таблицы с id вставляется в инпут и редактируется там
    @RequestMapping(value="/edit", method= RequestMethod.POST)
    public String updateUser(@RequestParam("userId") Long userId, Model model) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()){
            String phoneNumber = user.get().getPhoneNumber().replaceAll("[-()\\s]", "");
            String firstName = user.get().getFirstName();
            String lastName = user.get().getLastName();
            String email = user.get().getEmail();
            user = Optional.of(new User(firstName.substring(0, 1).toUpperCase() + firstName.substring(1),
                    lastName.substring(0, 1).toUpperCase() + lastName.substring(1),
                    phoneNumber, email));
        }
        model.addAttribute("addUser", user);
        //update user to database
        userService.delete(userId);
        return "users";
    }
    // а тут по id удаляется user
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("userId") Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }
}



