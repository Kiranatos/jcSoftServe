package com.softserve.itacademy;

import com.softserve.itacademy.Exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.util.ArrayList;


public class UserServiceTest {
    private static UserService userService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        annotationConfigContext.close();
    }

    @Test
    public void checkAddUser() {
        User user = new User("Keanu", "Reeves", "keanuReev@gmail.com", "548G637gu49", new ArrayList<>());
        User expected = new User("Keanu", "Reeves", "keanuReev@gmail.com", "548G637gu49", new ArrayList<>());
        User actual = userService.addUser(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkAddUserWithExistEmail() {
        User user = new User("John", "Wick", "johnWick@gmail.com", "root", new ArrayList<>());
        userService.addUser(user);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.addUser(user));
    }

    @Test
    public void checkUpdateUserByEmail() throws UserNotFoundException {
        User user = new User("Gats", "Brown", "gtsbrwn4@gmail.com", "b84ttb4etvR4", new ArrayList<>());
        userService.addUser(user);
        user = new User("Bruce", "Kent", "gtsbrwn4@gmail.com", "admin", new ArrayList<>());
        User expected = new User("Bruce", "Kent", "gtsbrwn4@gmail.com", "admin", new ArrayList<>());
        User actual = userService.updateUser(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkUpdateUserWhichNotExist() throws UserNotFoundException {
        User user = new User("Ghost", "Fake", "gjjfjfjfjdln@com", "vr77re8e7rf", new ArrayList<>());
        Assertions.assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(user));
    }

    @Test
    public void checkDeleteUser() {
        User user = new User("Delete", "User", "delusB@com", "6h5t6Rb5n4t", new ArrayList<>());
        userService.addUser(user);
        userService.addUser(new User("Delete2", "User2", "delus2B@com", "6h5t6R22b5n4t", new ArrayList<>()));
        userService.deleteUser(user);
        Assertions.assertFalse(userService.getAll().contains(user));
    }

    @Test
    public void checkGetAll() {
        Assertions.assertEquals(userService.getAll().size(), 3);
    }
}
