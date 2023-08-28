package com.novi.app;

import com.novi.app.model.Group;
import com.novi.app.model.User;
import com.novi.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
// @ContextConfiguration("classpath:application.properties")
class MainApplicationTests  extends AbstractTransactionalJUnit4SpringContextTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserService userService;

    @Test
    @Rollback
    public void test() {
        String firstName = "Tom";
        String lastName = "Cat";
        String phoneNumber = "79103421050";
        String email = "1@gmail.com";
        String login = String.valueOf(UUID.randomUUID());
        String password = "12345";
        String birthday = String.valueOf(LocalDate.of(1997,9,21));
        User user = new User(firstName,lastName,null,phoneNumber,email,login,password,birthday);
        userService.save(user);
    }
}
