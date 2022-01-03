package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ToDoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void getAllTasksTest() throws Exception {
        ToDo toDo = toDoService.readById(7L);
        List<Task> tasks = taskService.getByTodoId(7L);
        List<User> users = userService.getAll().stream()
                .filter(user -> user.getId() != toDo.getOwner().getId()).collect(Collectors.toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/7/tasks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo", "tasks", "users"))
                .andExpect(MockMvcResultMatchers.model().attribute("tasks", tasks))
                .andExpect(MockMvcResultMatchers.model().attribute("todo", toDo))
                .andExpect(MockMvcResultMatchers.model().attribute("users", users));
    }

    @Test
    @Transactional
    public void getAllToDosTest() throws Exception {
        List<ToDo> todos = toDoService.getByUserId(5L);
        User user = userService.readById(5L);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/all/users/5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todos", "user"))
                .andExpect(MockMvcResultMatchers.model().attribute("todos", todos))
                .andExpect(MockMvcResultMatchers.model().attribute("user", user));
    }

    @Test
    @Transactional
    public void createNewToDoPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/create/users/6"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo", "ownerId"))
                .andExpect(MockMvcResultMatchers.model().attribute("todo", new ToDo()))
                .andExpect(MockMvcResultMatchers.model().attribute("ownerId", 6L));
    }

    @Test
    @Transactional
    public void updateToDoPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/8/update/users/4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"))
                .andExpect(MockMvcResultMatchers.model().attribute("todo", toDoService.readById(8L)));
    }

    @Test
    @Transactional
    public void correctCreateNewToDoTest() throws Exception {
        ToDo toDo = new ToDo();
        toDo.setTitle("TestTodo");

        mockMvc.perform(MockMvcRequestBuilders.post("/todos/create/users/6")
                        .flashAttr("todo", toDo))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        List<ToDo> myTodos = userService.readById(6).getMyTodos();
        Assertions.assertEquals(3, myTodos.size());
    }

    @Test
    @Transactional
    public void correctUpdateToDoTest() throws Exception {
        ToDo toDo = new ToDo();
        toDo.setId(11L);
        toDo.setTitle("TestTodo");
        toDo.setCreatedAt(LocalDateTime.now());

        mockMvc.perform(MockMvcRequestBuilders.post("/todos/11/update/users/5")
                        .flashAttr("todo", toDo))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        ToDo expected = toDoService.readById(11L);
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.getTitle(),"TestTodo");
    }

    @Test
    @Transactional
    public void incorrectCreateNewToDoTest() throws Exception {
        ToDo toDo = new ToDo();
        toDo.setTitle("");

        mockMvc.perform(MockMvcRequestBuilders.post("/todos/create/users/6")
                        .flashAttr("todo", toDo))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-todo"));

        List<ToDo> myTodos = userService.readById(6).getMyTodos();
        Assertions.assertEquals(2, myTodos.size());
    }

    @Test
    @Transactional
    public void incorrectUpdateToDoTest() throws Exception {
        ToDo toDo = new ToDo();
        toDo.setId(11L);
        toDo.setTitle("");
        toDo.setCreatedAt(LocalDateTime.now());

        mockMvc.perform(MockMvcRequestBuilders.post("/todos/11/update/users/5")
                        .flashAttr("todo", toDo))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("update-todo"));
    }

    @Test
    @Transactional
    public void deleteToDoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/9/delete/users/4"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/todos/all/users/4"));

        Throwable exception = Assertions.assertThrows(EntityNotFoundException.class, () -> toDoService.readById(9L));
        Assertions.assertEquals("To-Do with id 9 not found", exception.getMessage());
        Assertions.assertEquals(EntityNotFoundException.class, exception.getClass());
    }

    @Test
    @Transactional
    void addCollaborator() throws Exception {
        long userId = 6L;
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/11/add").param("user_id", String.valueOf(userId)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/todos/11/tasks"));

        List<User> collab = new ArrayList<>();
        collab.add(userService.readById(6L));
        Assertions.assertArrayEquals(collab.toArray(), toDoService.readById(11L).getCollaborators().toArray());
    }

    @Test
    @Transactional
    void removeCollaborator() throws Exception {
        long userId = 6L;
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/7/remove").param("user_id", String.valueOf(userId)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/todos/7/tasks"));

        List<User> collab = new ArrayList<>();
        collab.add(userService.readById(5L));
        Assertions.assertArrayEquals(collab.toArray(), toDoService.readById(7L).getCollaborators().toArray());
    }
}
