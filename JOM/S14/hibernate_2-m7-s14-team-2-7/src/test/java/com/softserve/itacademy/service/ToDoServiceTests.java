package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class ToDoServiceTests {

    StateService stateService;
    TaskService taskService;
    ToDoService toDoService;
    UserService userService;
    RoleService roleService;

    @Autowired
    public ToDoServiceTests(RoleService roleService, StateService stateService,
                            TaskService taskService, ToDoService toDoService, UserService userService) {
        this.stateService = stateService;
        this.taskService = taskService;
        this.toDoService = toDoService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Test
    public void createTest() {
        Role role = roleService.readById(1L);
        User user = new User();
        user.setLastName("Lastname");
        user.setFirstName("Firstname");
        user.setEmail("email@mail.ru");
        user.setPassword("qwQW12!@");
        user.setRole(role);
        userService.create(user);
        ToDo toDo = new ToDo();
        toDo.setTitle("Title");
        toDo.setOwner(user);
        toDoService.create(toDo);
        assertEquals(11, toDoService.getAll().size());
    }

    @Test
    public void updateTest() {
        Role role = new Role();
        role.setName("Name");
        roleService.create(role);
        User user = new User();
        user.setLastName("Lastnggame");
        user.setFirstName("Firstnaggme");
        user.setEmail("email@magil.ru");
        user.setPassword("qwQW12g!@");
        user.setRole(role);
        userService.create(user);
        ToDo toDo = new ToDo();
        toDo.setTitle("Title");
        toDo.setOwner(user);
        toDoService.create(toDo);
        toDo.setTitle("Title2");
        toDoService.update(toDo);
        assertEquals("Title2", toDoService.readById(23).getTitle());
    }

    @Test
    public void readById() {
        assertEquals(10, toDoService.readById(10).getId());
    }

    @Test
    public void deleteTest() {
        toDoService.delete(13);
        assertEquals(10, toDoService.getAll().size());
    }

    @Test
    public void getAllToDosByUserIdTest() {
        int expectedSize = 2;
        List<ToDo> toDos = toDoService.getByUserId(5L);
        assertEquals(expectedSize, toDos.size());
    }
}