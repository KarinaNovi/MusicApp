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

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUser(Model model) {
        model.addAttribute("addUser", new User());
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String getUser( @ModelAttribute("user") User user, BindingResult bindingResult,
                           Model model) {
        model.addAttribute("addUser", new User());
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String middleName = user.getMiddleName();
        String phoneNumber = user.getPhoneNumber();
        String email = user.getEmail();
        String login = user.getUserLogin();
        String password = user.getPassword();
        String birthday = user.getBirthday();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users";
        }
        user = new User(firstName,
                lastName,
                middleName,
                phoneNumber,
                email,
                login,
                password,
                birthday);
        userService.save(user);
        return "redirect:/users";
    }

    @RequestMapping(value="/edit", method= RequestMethod.POST)
    public String updateUser(@RequestParam("userId") Long userId, Model model) {
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            String phoneNumber = user.getPhoneNumber().replaceAll("[-()\\s]", "");
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String middleName = user.getMiddleName();
            String login = user.getUserLogin();
            String password = user.getPassword();
            String birthday = user.getBirthday();
            String email = user.getEmail();
            optionalUser = Optional.of(new User(firstName,
                    lastName,
                    middleName,
                    phoneNumber,
                    email,
                    login,
                    password,
                    birthday));
        }
        model.addAttribute("addUser", optionalUser);
        // WA for dropping sequence
        userService.delete(userId);
        return "users";
    }

    // TODO: delete user is to set deletionDtm = sysdate, not physical deletion from DB
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("userId") Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }
}



