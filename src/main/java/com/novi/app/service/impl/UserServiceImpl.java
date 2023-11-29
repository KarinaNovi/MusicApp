package com.novi.app.service.impl;

import com.novi.app.model.Group;
import com.novi.app.model.MusicInstrument;
import com.novi.app.model.MusicStyle;
import com.novi.app.service.UserService;
import com.novi.app.util.Constants;
import org.springframework.stereotype.Service;
import com.novi.app.model.User;
import com.novi.app.model.repository.UserRepository;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    //TODO: add logger

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // TODO: looks like can be simplified
    @Override
    public Set<Group> getUserGroups(Long userId) {
        Optional<User> optionalUser = findUserById(userId);
        Set<Group> userGroups = new HashSet<>();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userGroups.addAll(user.getGroups());
        } else {
            System.out.println("WARN: No existing user with such id");
        }
        return userGroups;
    }

    @Override
    public Set<MusicStyle> getUserMusicStyles(Long userId) {
        Optional<User> optionalUser = findUserById(userId);
        Set<MusicStyle> musicStyles = new HashSet<>();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            musicStyles.addAll(user.getMusicStyles());
        } else {
            System.out.println("WARN: No existing user with such id");
        }
        return musicStyles;
    }

    @Override
    public Set<MusicInstrument> getUserMusicInstruments(Long userId) {
        Optional<User> optionalUser = findUserById(userId);
        Set<MusicInstrument> musicInstruments = new HashSet<>();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            musicInstruments.addAll(user.getMusicInstruments());
        } else {
            System.out.println("WARN: No existing user with such id");
        }
        return musicInstruments;
    }

    @Override
    public Set<Group> getGroupsOfLeader(Long userId) {
        Optional<User> optionalUser = findUserById(userId);
        Set<Group> groupsOfCurrentLeader = new HashSet<>();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            for (Group group : user.getGroups()) {
                if (user.getUserId() == group.getLeaderId()) {
                    groupsOfCurrentLeader.add(group);
                }
            }
        } else {
            System.out.println("WARN: No existing user with such id");
        }
        return groupsOfCurrentLeader;
    }

    @Override
    public void updateUser(Long userId, User user) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            // TODO: how to update only changed fields and don't perform saving of potential incorrect user from UI?
            user.setUserId(userId);
            userRepository.save(user);
        } else {
            System.out.println("WARN: No existing user with such id");
        }
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void terminateUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();
            userToUpdate.setDeletionDate(new Date());
            userRepository.save(userToUpdate);
        } else {
            System.out.println("WARN: No existing user with such id");
        }
    }

    @Override
    public List<User> findNewlyCreatedUsers() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.MONTH, -1);
        return userRepository.findNewlyCreatedUsers(currentDate.getTime());
    }

    @Override
    public List<User> findActiveUsers() {
        return userRepository.findActiveUsers(new Date(Constants.MAX_DATE));
    }

    // TODO: Users in groups automatically inherit music style of group
    @Override
    public List<User> getUsersWithCurrentMusicStyle(int styleId) {
        return userRepository
                .findAll()
                .stream()
                .filter(user -> !(user.getMusicStyles()
                        .stream()
                        .filter(musicStyle -> musicStyle.getStyleId() == styleId)
                        .toList().isEmpty()))
                .toList();
    }
}