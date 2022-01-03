package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Role;
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
public class RoleServiceTests {

    RoleService roleService;
    UserService userService;

    @Autowired
    public RoleServiceTests(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Test
    @Transactional
    public void createRoleTest() {
        Role expected = new Role();
        expected.setName("NEW");
        expected = roleService.create(expected);
        assertNotEquals(0, expected.getId());
    }

    @Test
    @Transactional
    public void getRoleById() {
        Role expected = new Role();
        expected.setName("ADMIN");
        Role actual = roleService.readById(1L);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    @Transactional
    public void updateRoleTest() {
        String newName = "NEW 2";
        Role role = roleService.readById(1L);
        role.setName(newName);
        Role actual = roleService.update(role);
        assertEquals(newName, actual.getName());
    }

    @Test
    @Transactional
    public void getAllRoleTest() {
        int expectedSize = 2;
        List<Role> roles = roleService.getAll();
        assertEquals(expectedSize, roles.size());
    }

    @Test
    public void exceptionWhenDeleteRoleWithUserTest() {
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            roleService.delete(1L);
        });
    }

    @Test
    @Transactional
    public void deleteRoleWithUserTest() {
        userService.delete(4L);
        roleService.delete(1L);
        int expectedSize = 1;
        List<Role> roles = roleService.getAll();
        assertEquals(expectedSize, roles.size());
    }
}
