package com.novi.app.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.novi.app.model.User;
import com.novi.app.service.UserService;

import java.util.*;

// TODO: align with new html, for now only for tests
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> index(){
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> show(@PathVariable("id") Long userId){
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

//    @GetMapping("/new")
//    public String newPerson(@ModelAttribute User user){
//        return "users/new";
//    }

    @PostMapping("/new")
    public ResponseEntity<Optional<User>> create(@Valid @RequestBody User user,
                                                 BindingResult bindingResult) {
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String middleName = user.getMiddleName();
            String phoneNumber = user.getPhoneNumber();
            String email = user.getEmail();
            String login = user.getUserLogin();
            String password = user.getPassword();
            String birthday = user.getBirthday();
        if (bindingResult.hasErrors()) {
            System.out.println("Mandatory parameter is null, check request body");
            return new ResponseEntity<>(Optional.empty(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user = new User(firstName,
                lastName,
                middleName,
                phoneNumber,
                email,
                login,
                password,
                birthday);
        userService.saveUser(user);
        return new ResponseEntity<>(userService.findUserById(user.getUserId()), HttpStatus.OK);
    }

//    @GetMapping("{id}/edit")
//    public String edit(Model model, @PathVariable("id") Long userId){
//        model.addAttribute("addUser", userService.findUserById(userId));
//        return "users/edit";
//    }

    @PostMapping("/updateUser/{id}")
    public ResponseEntity<Optional<User>> update(@RequestBody User user,
                                                 @PathVariable("id") Long userId){
        //TODO: validate - which parameters need to be updated, which need to be taken as is
        userService.updateUser(userId, user);
        return new ResponseEntity<>(userService.findUserById(user.getUserId()), HttpStatus.OK);
    }

    // delete = set DeletionDtm as sysdate only
    // TODO: need to switch to @RequestBody after refactoring
    @RequestMapping(value = "/terminateUser/{id}", method = RequestMethod.POST)
    public ResponseEntity<Optional<User>> terminateUser(@PathVariable("id") Long userId) {
        Optional<User> user = userService.findUserById(userId);
        user.ifPresent(value -> value.setDeletionDtm(new Date()));
        userService.terminateUser(userId);
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    // Not recommended physical deletion - manual corrections only!
    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<List<User>> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}



