package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.TaskDto;
import com.softserve.itacademy.dto.TaskTransformer;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.service.StateService;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private StateService stateService;

    @Test
    @Transactional
    public void createNewTaskPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/create/todos/7"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("task"))
                .andExpect(MockMvcResultMatchers.model().attribute("task", new TaskDto()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"))
                .andExpect(MockMvcResultMatchers.model().attribute("todo", toDoService.readById(7L)))
                .andExpect(MockMvcResultMatchers.model().attributeExists("priorities"))
                .andExpect(MockMvcResultMatchers.model().attribute("priorities", Priority.values()));
    }

    @Test
    @Transactional
    public void updateTaskPageTest() throws Exception {
        TaskDto expectedTaskDto = TaskTransformer.convertToDto(taskService.readById(7L));

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/7/update/todos/7"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("task"))
                .andExpect(MockMvcResultMatchers.model().attribute("task", expectedTaskDto))
                .andExpect(MockMvcResultMatchers.model().attributeExists("states"))
                .andExpect(MockMvcResultMatchers.model().attribute("states", stateService.getAll()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("priorities"))
                .andExpect(MockMvcResultMatchers.model().attribute("priorities", Priority.values()));
    }

    @Test
    @Transactional
    public void correctCreationNewTaskTest() throws Exception {
        TaskDto taskDto = new TaskDto();
        taskDto.setTodoId(8L);
        taskDto.setName("Test");
        taskDto.setPriority(Priority.HIGH.name());

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/create/todos/8")
                        .flashAttr("task", taskDto))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Assertions.assertFalse(taskService.getByTodoId(8L).isEmpty());
        TaskDto task = TaskTransformer.convertToDto(taskService.getByTodoId(8L).get(0));
        Assertions.assertEquals(task.getName(), "Test");
        Assertions.assertEquals(task.getPriority(), String.valueOf(Priority.HIGH));
    }

    @Test
    @Transactional
    public void correctUpdateTaskTest() throws Exception {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(5L);
        taskDto.setTodoId(7L);
        taskDto.setName("TestUpdated");
        taskDto.setStateId(6L);
        taskDto.setPriority(Priority.LOW.name());

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/5/update/todos/7")
                        .flashAttr("task", taskDto))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Task task = taskService.readById(5L);
        Assertions.assertEquals(task.getName(), taskDto.getName());
        Assertions.assertEquals(task.getPriority().toString(), taskDto.getPriority());
        Assertions.assertEquals(task.getState().getId(), taskDto.getStateId());
    }

    @Test
    @Transactional
    public void incorrectCreateNewTaskTest() throws Exception {
        TaskDto taskDto = new TaskDto();
        taskDto.setTodoId(8L);
        taskDto.setName("");
        taskDto.setPriority(Priority.HIGH.name());

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/create/todos/8")
                        .flashAttr("task", taskDto))
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo", "priorities"))
                .andExpect(MockMvcResultMatchers.model().attribute("todo", toDoService.readById(8)))
                .andExpect(MockMvcResultMatchers.model().attribute("priorities", Priority.values()));

        Assertions.assertTrue(taskService.getByTodoId(8L).isEmpty());
    }

    @Test
    @Transactional
    public void incorrectUpdateTaskTest() throws Exception {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(5L);
        taskDto.setTodoId(7L);
        taskDto.setName("");
        taskDto.setStateId(6L);
        taskDto.setPriority(Priority.LOW.name());

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/5/update/todos/7")
                        .flashAttr("task", taskDto))
                .andExpect(MockMvcResultMatchers.model().attributeExists("states", "priorities"))
                .andExpect(MockMvcResultMatchers.model().attribute("states", stateService.getAll()))
                .andExpect(MockMvcResultMatchers.model().attribute("priorities", Priority.values()));

        Task task = taskService.readById(5L);
        Assertions.assertNotEquals(task.getName(), taskDto.getName());
        Assertions.assertNotEquals(task.getPriority().toString(), taskDto.getPriority());
        Assertions.assertNotEquals(task.getState().getId(), taskDto.getStateId());
    }

    @Test
    @Transactional
    public void deleteTaskTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/5/delete/todos/7"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/todos/7/tasks"));

        Throwable exception = Assertions.assertThrows(EntityNotFoundException.class, () -> taskService.readById(5L));
        Assertions.assertEquals("Task with id 5 not found", exception.getMessage());
        Assertions.assertEquals(EntityNotFoundException.class, exception.getClass());
    }
}
