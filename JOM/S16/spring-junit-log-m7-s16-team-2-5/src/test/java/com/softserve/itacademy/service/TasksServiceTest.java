package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
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
public class TasksServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @Test
    @Transactional
    public void getAllTasksTest() throws Exception {
        List<Task> expected = taskService.getByTodoId(7L);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/todos/7/tasks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("tasks"))
                .andExpect(MockMvcResultMatchers.model().attribute("tasks", expected));
    }

    @Test
    @Transactional
    public void createNewTaskTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/tasks/create/todos/7")
                .param("name", "Task #10")
                .param("priority", Priority.values()[2].toString())
                .param("todoId", "7"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Transactional
    public void updateTaskTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/tasks/5/update/todos/8")
                .param("name", "Task #111")
                .param("priority", Priority.values()[0].toString())
                .param("todoId", "8")
                .param("stateId", "8")
                .param("id", "5"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Transactional
    public void deleteTaskTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/tasks/6/delete/todos/7"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Transactional
    public void getError404WhenDeleteNotExistTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/tasks/666/delete/todos/7"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Transactional
    public void getError404WhenCallNotExistTodo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/todos/100/tasks"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
