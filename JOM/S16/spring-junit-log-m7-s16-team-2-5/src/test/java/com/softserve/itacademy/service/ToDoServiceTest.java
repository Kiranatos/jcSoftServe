package com.softserve.itacademy.service;

import com.softserve.itacademy.model.ToDo;
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
public class ToDoServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoService toDoService;

    @Test
    @Transactional
    public void createToDoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/todos/create/users/5")
                .param("title", "Test ToDo"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Transactional
    public void updateToDoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/todos/7/update/users/4")
                .param("title", "New Name Test ToDo")
                .param("id", "7")
                .param("userId", "4"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Transactional
    public void deleteToDoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/todos/7/delete/users/4"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Transactional
    public void addCollaboratorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/todos/7/add")
                .param("user_id", "5"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Transactional
    public void deleteCollaboratorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/todos/7/remove")
                .param("user_id", "4"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Transactional
    public void getAllToDosTest() throws Exception {
        List<ToDo> todos = toDoService.getByUserId(4L);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/todos/all/users/4"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("todos"))
                .andExpect(MockMvcResultMatchers.model().attribute("todos", todos))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()
                );
    }
}