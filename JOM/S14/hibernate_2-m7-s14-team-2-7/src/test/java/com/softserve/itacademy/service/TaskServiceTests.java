package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class TaskServiceTests {

    StateService stateService;
    TaskService taskService;
    ToDoService toDoService;
    UserService userService;
    RoleService roleService;

    @Autowired
    public TaskServiceTests(StateService stateService, TaskService taskService,
            ToDoService toDoService, UserService userService, RoleService roleService) {
        this.stateService = stateService;
        this.taskService = taskService;
        this.toDoService = toDoService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Test
    public void createTask() {
        Task task = new Task();
        task.setName("Name");
        task.setPriority(Priority.HIGH);
        taskService.create(task);
        assertEquals(5, taskService.getAll().size());
    }

    @Test
    public void updateTask() {
        Task task = new Task();
        task.setName("Name11");
        task.setPriority(Priority.HIGH);
        taskService.create(task);
        task.setName("Name");
        taskService.update(task);
        assertEquals("Name", taskService.readById(10).getName());
    }

    @Test
    public void readByIdTest() {
        assertEquals("Task #2", taskService.readById(6L).getName());
    }

    @Test
    public void deleteTest() {
        taskService.delete(6);
        assertEquals(4, taskService.getAll().size());
    }

    @Test
    public void getByToDoId() {
        assertEquals(3, taskService.getByTodoId(7).size());
    }
}
