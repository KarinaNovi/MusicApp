package com.novi.app.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.novi.app.model.User;
import com.novi.app.service.UserService;
import com.novi.app.service.validator.UserValidator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(Model model) {
        model.addAttribute("addUser", new User());
        model.addAttribute("users", userService.findAll());
        return "users";
    }

//    @GetMapping
//    public String index(Model model){
//        model.addAttribute("users",userService.findAll());
//        return "users";
//    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long userId, Model model){
        model.addAttribute("user", userService.findById(userId));
        return "users/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute User user){
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute @Valid User user,
                         BindingResult bindingResult){
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") Long userId){
        model.addAttribute("addUser", userService.findById(userId));
        return "users/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id") Long userId){
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        userService.update(userId, user);
        return "redirect:/users";
    }

    // delete = set DeletionDtm as sysdate only
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("userId") Long userId) {
        Optional<User> user = userService.findById(userId);
        SimpleDateFormat customDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        user.ifPresent(value -> value.setDeletionDtm(customDateFormat.format(new Date())));
        userService.delete(userId);
        return "redirect:/users";
    }

    // Not recommended physical deletion - manual corrections only!
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public String deleteUserFromDB(@RequestParam("userId") Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }
}



