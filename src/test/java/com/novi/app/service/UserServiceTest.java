package com.novi.app.service;

import com.novi.app.model.Group;
import com.novi.app.model.User;
import com.novi.app.model.request.ModifyUserRequest;
import com.novi.app.service.testData.TestUser;
import com.novi.app.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(
            UserServiceTest.class
    );

    @Autowired
    private UserService userService;

    @Test
    @Rollback
    public void saveAndPhysicalDeleteTests() {
        User user = TestUser.createSimpleUser();
        userService.saveUser(user);
        Optional<User> checkSuccessfulSave = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulSave.isPresent());
        userService.deleteUser(user.getUserId());
        Optional<User> checkSuccessfulDelete = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulDelete.isEmpty());
    }

    @Test
    @Rollback
    public void testUpdateUser() {
        User user = TestUser.createSimpleUser();
        userService.saveUser(user);
        Optional<User> checkSuccessfulSave = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulSave.isPresent());
        ModifyUserRequest modifyUserRequest = new ModifyUserRequest();
        modifyUserRequest.setUserLogin("updated_login");
        userService.updateUser(user.getUserId(), modifyUserRequest);
        Optional<User> checkSuccessfulUpdate = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulUpdate.isPresent());
        Assertions.assertEquals("updated_login", checkSuccessfulUpdate.get().getUserLogin());
        cleanUp(user);
    }

    @Test
    public void testPrintNewlyCreatedUsers() {
        Calendar testDate = new GregorianCalendar();
        testDate.add(Calendar.MONTH, -1);
        List<String> newUsersNames = userService
                .findAllUsers()
                .stream()
                .filter(user -> testDate.getTime().before(user.getRegistrationDate()))
                .map(User::getUserLogin)
                .collect(Collectors.toList());
        Assertions.assertEquals(newUsersNames, userService.findNewlyCreatedUsers().stream().map(User::getUserLogin).toList());
    }

    @Test
    @Rollback
    public void testTerminationUser() {
        User user = TestUser.createSimpleUser();
        userService.saveUser(user);
        Optional<User> checkSuccessfulSave = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulSave.isPresent());
        userService.terminateUser(user.getUserId());
        Optional<User> checkSuccessfulTermination = userService.findUserById(user.getUserId());
        if (checkSuccessfulTermination.isPresent()) {
            Date deletionDate = checkSuccessfulTermination.get().getDeletionDate();
            Assertions.assertNotEquals(deletionDate, new Date(Constants.MAX_DATE));
        }
        cleanUp(user);
    }

    @Test
    public void testPrintActiveUsers() {
        List<String> activeUsersNames = userService
                .findAllUsers()
                .stream()
                .filter(user -> new Date(Constants.MAX_DATE).equals(user.getDeletionDate()))
                .map(User::getUserLogin)
                .collect(Collectors.toList());
        Assertions.assertEquals(activeUsersNames, userService.findActiveUsers().stream().map(User::getUserLogin).toList());
    }

    // Example with existing data
    @Test
    public void testGetGroupOfLeader() {
        User user = userService.findUserById(75L).orElseThrow();
        Set<Group> groupsOfCurrentLeader = userService.getGroupsOfLeader(75L);
        logger.info(user.getFirstName() + ": " + groupsOfCurrentLeader
                .stream()
                .map(Group::getGroupName).toList());
    }

    /**
        Example for getting specific info of current user
     */
    @Test
    public void testPrintGroupsOfUser() {
        User testUser = userService
                .findAllUsers()
                .stream()
                .filter(user -> !user.getGroups().isEmpty())
                .findAny()
                .orElseThrow();
        Set<Group> groups = userService.getUserGroups(testUser.getUserId());
        logger.info(testUser.getFirstName() + ": " + groups.stream().map(Group::getGroupName).toList());
    }

    @Test
    public void testGetUsersWithSpecificStyle() {
        logger.info(userService.getUsersWithCurrentMusicStyle(1).toString());
    }

    // clean up after test - temporary till test DB
    public void cleanUp(User user) {
        userService.deleteUser(user.getUserId());
        Optional<User> checkSuccessfulDelete = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulDelete.isEmpty());
    }
}
