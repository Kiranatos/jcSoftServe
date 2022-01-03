package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class UserServiceTests {

    UserService userService;
    RoleService roleService;

    @Autowired
    public UserServiceTests(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Test
    @Transactional
    public void createUserTest() {
        Role role = roleService.readById(1L);
        User expected = new User();
        expected.setFirstName("Carl");
        expected.setLastName("Blue");
        expected.setEmail("carl@gmail.com");
        expected.setPassword("qwQW12!@");
        expected.setRole(role);
        expected = userService.create(expected);
        assertNotEquals(0, expected.getId());
    }

    @Test
    public void getUserById() {
        User expected = new User();
        expected.setFirstName("Nick");
        expected.setId(5L);
        User actual = userService.readById(5L);
        assertEquals(expected.getFirstName(), actual.getFirstName());
    }

    @Test
    public void getUserByEmail() {
        User expected = new User();
        expected.setEmail("nora@mail.com");
        expected.setId(6L);
        assertEquals(expected.getEmail(), userService.readById(6L).getEmail());
    }

    @Test
    @Transactional
    public void updateUserTest() {
        String newName = "Sophia";
        User user = userService.readById(4L);
        user.setFirstName(newName);
        User actual = userService.update(user);
        assertEquals(newName, actual.getFirstName());
    }

    @Test
    public void getAllUsersTest() {
        int expectedSize = 3;
        List<User> users = userService.getAll();
        assertTrue(expectedSize <= users.size(), String.format("At least %d users shuold be in users table", expectedSize));
    }

    @Test
    @Transactional
    public void deleteUserTest() {
        userService.delete(4L);
        userService.delete(5L);
        userService.delete(6L);
        int expectedSize = 4;
        List<User> users = userService.getAll();
        assertEquals(expectedSize, users.size());
    }
}