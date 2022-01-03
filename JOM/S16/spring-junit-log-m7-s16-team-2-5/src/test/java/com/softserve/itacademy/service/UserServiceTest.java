package com.softserve.itacademy.service;

import com.softserve.itacademy.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void createUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/create")
                .param("firstName", "First Name")
                .param("lastName", "Last Name")
                .param("password", "Password")
                .param("email", "Email@softserve.com"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @Transactional
    public void updateUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/5/update")
                .param("id", "5")
                .param("firstName", "Newfirstname")
                .param("lastName", "Lastame")
                .param("password", "Password")
                .param("email", "Email@softserve.com")
                .param("roleId", "2"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Transactional
    public void deleteUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/5/delete")
                .param("id", "5"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        List<User> users = userService.getAll();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/all"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}