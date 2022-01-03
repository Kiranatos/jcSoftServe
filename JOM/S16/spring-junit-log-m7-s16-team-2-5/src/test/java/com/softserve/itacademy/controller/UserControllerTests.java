package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test
    @Transactional
    public void getAllUsersTest() throws Exception {
        List<User> expected = userService.getAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", expected));
    }

    @Test
    @Transactional
    public void readUserTest() throws Exception {
        User expected = userService.readById(5L);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/5/read"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", expected));
    }

    @Test
    @Transactional
    public void createNewUserPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", new User()));
    }

    @Test
    @Transactional
    public void updateUserPageTest() throws Exception {
        User expected = userService.readById(5L);
        List<Role> expectedRoles = roleService.getAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/5/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user", "roles"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", expected))
                .andExpect(MockMvcResultMatchers.model().attribute("roles", expectedRoles));
    }

    @Test
    @Transactional
    public void correctCreationNewUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .param("firstName", "Test")
                        .param("lastName", "Test")
                        .param("email", "test@gmail.com")
                        .param("password", "12345"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        User expected = userService.getUserByEmail("test@gmail.com");
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.getFirstName(),"Test");
        Assertions.assertEquals(expected.getLastName(),"Test");
        Assertions.assertEquals(expected.getEmail(),"test@gmail.com");
        Assertions.assertEquals(expected.getPassword(),"12345");
    }

    @Test
    @Transactional
    public void correctUpdateUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/5/update")
                        .param("firstName", "Updated")
                        .param("lastName", "Updated")
                        .param("email", "Updated@gmail.com")
                        .param("password", "Updated12345")
                        .param("roleId", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        User expected = userService.readById(5L);
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.getFirstName(),"Updated");
        Assertions.assertEquals(expected.getLastName(),"Updated");
        Assertions.assertEquals(expected.getEmail(),"Updated@gmail.com");
        Assertions.assertEquals(expected.getPassword(),"Updated12345");
    }

    @Test
    @Transactional
    public void incorrectCreationNewUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("email", "test@gmail.com")
                        .param("password", "12345"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-user"));

        Assertions.assertEquals(3 , userService.getAll().size());
    }

@Test
    public void shouldUpdateUser() throws Exception {
        // Change FirstName and Role (only Admin can change his Role)
        Long userId = 4L;
        String newFirstName = "Alex";
        User oldUser = userService.readById(userId);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userId + "/update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", oldUser))
                .andExpect(MockMvcResultMatchers.model().attributeExists("roles"))
                .andExpect(MockMvcResultMatchers.model().attribute("roles", roleService.getAll()));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + userId + "/update")
                .param("firstName", newFirstName)
                .param("lastName", oldUser.getLastName())
                .param("oldPassword", oldUser.getPassword())
                .param("password", oldUser.getPassword())
                .param("email", oldUser.getEmail())
                .param("roleId", "2"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        User user = userService.readById(userId);
        oldUser.setFirstName(newFirstName);
        oldUser.setRole(roleService.readById(2L));
        Assertions.assertEquals(user, oldUser);
    }

    @Test
    @Transactional
    public void deleteUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/5/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Throwable exception = Assertions.assertThrows(EntityNotFoundException.class, () -> userService.readById(5L));
        Assertions.assertEquals("User with id 5 not found", exception.getMessage());
        Assertions.assertEquals(EntityNotFoundException.class, exception.getClass());
    }
}
