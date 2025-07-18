package com.novi.app.controller;

import com.novi.app.model.Group;
import com.novi.app.model.MusicInstrument;
import com.novi.app.model.MusicStyle;
import com.novi.app.model.request.CreateUserRequest;
import com.novi.app.model.request.ModifyUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.novi.app.model.User;
import com.novi.app.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
@Tag(
        name = "Пользователи",
        description = "Все методы для работы с пользователями системы"
)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(
            UserController.class
    );

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получить информацию о пользователях")
    @GetMapping("/all")
    public ResponseEntity<List<User>> index(){
        logger.debug("Getting all users");
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Получить информацию о пользователе по его id")
    // а если он хочет скрыть какую-то инфу, например, др?
    @PreAuthorize("hasRole('USER'+#userId)")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@Parameter(description = "id пользователя")
                                                      @PathVariable("id") Long userId){
        logger.debug("Getting user info with id: {}", userId);
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    @Operation(summary = "Получить информацию о группах пользователя")
    @GetMapping("/{id}/groups")
    public ResponseEntity<Set<Group>> getUserGroups(@Parameter(description = "id пользователя")
                                                    @PathVariable("id") Long userId){
        return new ResponseEntity<>(userService.getUserGroups(userId), HttpStatus.OK);
    }

    @Operation(summary = "Получить информацию о музыкальных стилях пользователя")
    @GetMapping("/{id}/styles")
    public ResponseEntity<Set<MusicStyle>> getUserMusicStyles(@Parameter(description = "id пользователя")
                                                              @PathVariable("id") Long userId){
        return new ResponseEntity<>(userService.getUserMusicStyles(userId), HttpStatus.OK);
    }

    @Operation(summary = "Получить информацию о музыкальных инструментах пользователя")
    @GetMapping("/{id}/instruments")
    public ResponseEntity<Set<MusicInstrument>> getUserMusicInstruments(@Parameter(description = "id пользователя")
                                                                        @PathVariable("id") Long userId){
        return new ResponseEntity<>(userService.getUserMusicInstruments(userId), HttpStatus.OK);
    }

    // @ExceptionHandler
    @Operation(summary = "Добавить музыкальный стиль пользователю")
    @PostMapping("/{userId}/styles/{styleId}")
    @PreAuthorize("hasRole('USER'+#userId)")
    public ResponseEntity<String> addMusicStyleIntoUserList(@Parameter(description = "id пользователя")
                                                                        @PathVariable("userId") Long userId,
                                                                        @PathVariable("styleId") Integer styleId){
        userService.addMusicStyle(userId, styleId);
        return new ResponseEntity<>("Music style has been added", HttpStatus.OK);
    }

    @Operation(summary = "Добавить музыкальный инструмент пользователю")
    @PostMapping("/{userId}/instruments/{instrumentId}")
    @PreAuthorize("hasRole('USER'+#userId)")
    public ResponseEntity<String> addMusicInstrumentIntoUserList(@Parameter(description = "id пользователя")
                                                                 @PathVariable("userId") Long userId,
                                                                 @PathVariable("instrumentId") Integer instrumentId){
        userService.addMusicInstrument(userId, instrumentId);
        return new ResponseEntity<>("Music instrument has been added", HttpStatus.OK);
    }

    @Operation(summary = "Добавить группу  пользователю")
    @PostMapping("/{userId}/groups/{groupId}")
    @PreAuthorize("hasRole('USER'+#userId)")
    public ResponseEntity<String> addGroupIntoUserList(@Parameter(description = "id пользователя")
                                                                 @PathVariable("userId") Long userId,
                                                                 @PathVariable("groupId") Long groupId){
        userService.addGroup(userId, groupId);
        return new ResponseEntity<>("Group has been added", HttpStatus.OK);
    }

    @Operation(summary = "Получить информацию о группах лидера")
    @GetMapping("/{id}/leaderGroups")
    public ResponseEntity<Set<Group>> getLeaderGroups(@Parameter(description = "id пользователя")
                                                      @PathVariable("id") Long userId){
        return new ResponseEntity<>(userService.getGroupsOfLeader(userId), HttpStatus.OK);
    }

    @Operation(summary = "Создать нового пользователя")
    @PostMapping("/new")
    public ResponseEntity<User> create(@Valid @RequestBody CreateUserRequest createUserRequest) {
        logger.debug("Incoming request: {}", createUserRequest);
        User user = userService.createUser(createUserRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER_'+#userId)")
    @PostMapping("/updateUser/{id}")
    public ResponseEntity<Optional<User>> update(@Valid @RequestBody ModifyUserRequest modifyUserRequest,
                                                 @PathVariable("id") Long userId) {
        logger.trace("Incoming request: {}", modifyUserRequest);
        Optional<User> optionalUser = userService.updateUser(userId, modifyUserRequest);
        return new ResponseEntity<>(optionalUser, HttpStatus.OK);
    }

    // terminate = set deletionDate as sysdate only
    @PreAuthorize("hasRole('USER'+#userId) or hasRole('ADMIN')")
    @RequestMapping(value = "/terminateUser/{id}", method = RequestMethod.POST)
    public ResponseEntity<Optional<User>> terminateUser(@PathVariable("id") Long userId) {
        Optional<User> user = userService.findUserById(userId);
        user.ifPresent(value -> value.setDeletionDate(new Date()));
        userService.terminateUser(userId);
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    // Not recommended physical deletion - manual corrections only!
    @PreAuthorize("hasRole('ADMIN')")
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
}