package com.novi.app.controller;

import com.novi.app.model.Group;
import com.novi.app.model.MusicInstrument;
import com.novi.app.model.MusicStyle;
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
@RestController
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
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") Long userId){
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<Set<Group>> getUserGroups(@PathVariable("id") Long userId){
        return new ResponseEntity<>(userService.getUserGroups(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}/styles")
    public ResponseEntity<Set<MusicStyle>> getUserMusicStyles(@PathVariable("id") Long userId){
        return new ResponseEntity<>(userService.getUserMusicStyles(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}/instruments")
    public ResponseEntity<Set<MusicInstrument>> getUserMusicInstruments(@PathVariable("id") Long userId){
        return new ResponseEntity<>(userService.getUserMusicInstruments(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}/leaderGroups")
    public ResponseEntity<Set<Group>> getLeaderGroups(@PathVariable("id") Long userId){
        return new ResponseEntity<>(userService.getGroupsOfLeader(userId), HttpStatus.OK);
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
        return new ResponseEntity<>(userService.findUserById(user.getUserId()), HttpStatus.CREATED);
    }

//    @GetMapping("{id}/edit")
//    public String edit(Model model, @PathVariable("id") Long userId){
//        model.addAttribute("addUser", userService.findUserById(userId));
//        return "users/edit";
//    }

    @PostMapping("/updateUser/{id}")
    public ResponseEntity<Optional<User>> update(@RequestBody User user,
                                                 @PathVariable("id") Long userId) {
        //TODO: validate - which parameters need to be updated, which need to be taken as is
        userService.updateUser(userId, user);
        return new ResponseEntity<>(userService.findUserById(user.getUserId()), HttpStatus.OK);
    }

    // terminate = set deletionDate as sysdate only
    @RequestMapping(value = "/terminateUser/{id}", method = RequestMethod.POST)
    public ResponseEntity<Optional<User>> terminateUser(@PathVariable("id") Long userId) {
        Optional<User> user = userService.findUserById(userId);
        user.ifPresent(value -> value.setDeletionDate(new Date()));
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

    /**
     * Many users operations
     */
    @GetMapping("/activeUsers")
    public ResponseEntity<List<User>> getActiveUsers() {
        return new ResponseEntity<>(userService.findActiveUsers(), HttpStatus.OK);
    }

    @GetMapping("/newUsers")
    public ResponseEntity<List<User>> getNewUsers() {
        return new ResponseEntity<>(userService.findNewlyCreatedUsers(), HttpStatus.OK);
    }

    // TODO: move to musicController: /styles/{1}/users
    @GetMapping("/usersWithStyle/{id}")
    public ResponseEntity<List<User>> getUsersOfCurrentMusicStyle(@PathVariable("id") Integer musicStyleId) {
        return new ResponseEntity<>(userService.getUsersWithCurrentMusicStyle(musicStyleId), HttpStatus.OK);
    }
}