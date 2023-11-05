package com.novi.app.service;

import com.novi.app.model.User;
import com.novi.app.service.testData.TestUser;
import com.novi.app.util.Constants;
import com.novi.app.util.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

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
        user.setUserLogin("updated_login");
        userService.updateUser(user.getUserId(), user);
        Optional<User> checkSuccessfulUpdate = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulUpdate.isPresent());
        Assertions.assertEquals("updated_login", checkSuccessfulUpdate.get().getUserLogin());
        cleanUp(user);
    }

    @Test
    @Rollback
    public void testPrintNewlyCreatedUsers() {
        // TODO: create logger for such tests
        userService.findNewlyCreatedUsers().forEach(user -> System.out.println(user.getFirstName()));
    }

    @Test
    @Rollback
    public void testTerminationUser() {
        User user = TestUser.createSimpleUser();
        userService.saveUser(user);
        Optional<User> checkSuccessfulSave = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulSave.isPresent());
        userService.terminateUser(user);
        Optional<User> checkSuccessfulTermination = userService.findUserById(user.getUserId());
        if (checkSuccessfulTermination.isPresent()) {
            String deletionDate = checkSuccessfulTermination.get().getDeletionDtm();
            Assertions.assertNotEquals(deletionDate, UserUtil.formatDate(new Date(Constants.MAX_DATE)));
        }
        cleanUp(user);
    }

    @Test
    public void testPrintActiveUsers() {
        // TODO: create logger for such tests
        System.out.println(userService.findActiveUsers());
        userService.findActiveUsers().forEach(user -> System.out.println(user.getFirstName()));
    }

    // clean up after test - temporary till test DB
    public void cleanUp(User user) {
        userService.deleteUser(user.getUserId());
        Optional<User> checkSuccessfulDelete = userService.findUserById(user.getUserId());
        Assertions.assertTrue(checkSuccessfulDelete.isEmpty());
    }
}
