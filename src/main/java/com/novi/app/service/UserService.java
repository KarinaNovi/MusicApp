package com.novi.app.service;

import com.novi.app.model.Group;
import com.novi.app.model.MusicInstrument;
import com.novi.app.model.MusicStyle;
import com.novi.app.model.User;
import com.novi.app.model.request.CreateUserRequest;
import com.novi.app.model.request.ModifyUserRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    // single user operation
    // get info
    Optional<User> findUserById(Long userId);
    Set<Group> getUserGroups(Long userId);
    Set<MusicStyle> getUserMusicStyles(Long userId);
    Set<MusicInstrument> getUserMusicInstruments(Long userId);
    Set<Group> getGroupsOfLeader(Long userId);

    // update info
    void saveUser(User user);
    User createUser(CreateUserRequest createUserRequest);
    Optional<User> updateUser(Long userId, ModifyUserRequest modifyUserRequest);
    void addUserRole(String roleName, User user);
    void addMusicInstrument(Long userId, Integer instrumentId);
    void addMusicStyle(Long userId, Integer styleId);
    void addGroup(Long userId, Long groupId);
    void deleteUser(Long userId);
    void terminateUser(Long userId);

    // Many users operation
    List<User> findAllUsers();
    List<User> findNewlyCreatedUsers();
    List<User> findActiveUsers();
    List<User> getUsersWithCurrentMusicStyle(int styleId);
}