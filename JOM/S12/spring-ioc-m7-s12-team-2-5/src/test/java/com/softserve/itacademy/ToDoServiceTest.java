package com.softserve.itacademy;

import com.softserve.itacademy.Exceptions.ToDoNotFound;
import com.softserve.itacademy.Exceptions.UserNotFoundException;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.ToDoService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnitPlatform.class)
public class ToDoServiceTest {
    private static UserService userService;
    private static ToDoService toDoService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        toDoService = annotationConfigContext.getBean(ToDoService.class);
        annotationConfigContext.close();
    }

    @Test
    public void checkAddToDoSuccess () throws UserNotFoundException {
        User user1 = new User("FirstName", "LastName", "email1@gmail.com", "password", new ArrayList<>());
        userService.addUser(user1);
        ToDo expected = new ToDo("ToDo1", LocalDateTime.now(), user1, new ArrayList<>());
        ToDo actual = toDoService.addTodo(expected, user1);
        assertEquals(expected,actual);
    }

    @Test
    public void checkAddToDoException () throws UserNotFoundException {
        User user2 = new User("Oksana", "Moisiuk", "email2@gmail.com", "password");
        ToDo todo2 = new ToDo("ToDo2", LocalDateTime.now(), user2, new ArrayList<>());
        assertThrows(UserNotFoundException.class, ()-> toDoService.addTodo(todo2,user2));
    }

    @Test
    void updateTodoSuccess() throws UserNotFoundException, ToDoNotFound {
        User user3 = new User("Richard", "Parkins", "email3@gmail.com", "password", new ArrayList<>());
        userService.addUser(user3);
        ToDo expected = new ToDo("ToDo3", LocalDateTime.now(), user3, new ArrayList<>());
        ToDo todo = toDoService.addTodo(expected, user3);
        Task task = new Task("Task12", Priority.HIGH);
        ToDo toDoToBeUpdate = new ToDo(todo.getTitle(), todo.getCreatedAt(), todo.getOwner(), Arrays.asList(task));
        assertEquals(toDoToBeUpdate, toDoService.updateTodo(toDoToBeUpdate));
    }

    @Test
    void deleteTodoException() throws UserNotFoundException {
        User user4 = new User("Firs", "Last", "email4@gmail.com", "password", new ArrayList<>());
        userService.addUser(user4);
        ToDo todo = new ToDo("ToDoNotExist", LocalDateTime.now(), user4, new ArrayList<>());
        assertThrows(ToDoNotFound.class, ()->toDoService.deleteTodo(todo));
    }

    @Test
    void getByUserSuccess() throws UserNotFoundException {
        User user5 = new User("Name", "LName", "email5@gmail.com", "password", new ArrayList<>());
        userService.addUser(user5);
        ToDo todo = new ToDo("ToDo_11", LocalDateTime.now(), user5, new ArrayList<>());
        toDoService.addTodo(todo, user5);
        List<ToDo> expected = Arrays.asList(todo);
        assertEquals(expected, toDoService.getByUser(user5));
    }

    @Test
    void getByUserException() {
        User user6 = new User("Oksana", "Moisiuk", "email6@gmail.com", "password", new ArrayList<>());
        ToDo todo = new ToDo("ToDo_12", LocalDateTime.now(), user6, new ArrayList<>());
        assertThrows(UserNotFoundException.class, ()->toDoService.getByUser(user6));
    }

    @Test
    void getByUserTitleSuccess() throws UserNotFoundException, ToDoNotFound {
        User user7 = new User("John", "Robinson", "email7@gmail.com", "password", new ArrayList<>());
        userService.addUser(user7);
        ToDo todo = new ToDo("ToDo_13", LocalDateTime.now(), user7, new ArrayList<>());
        toDoService.addTodo(todo, user7);
        assertEquals(todo, toDoService.getByUserTitle(user7, "ToDo_13"));
    }

    @Test
    void getByUserTitleException() throws UserNotFoundException{
        User user8 = new User("Sara", "Jonson", "email8@gmail.com", "password", new ArrayList<>());
        userService.addUser(user8);
        ToDo todo = new ToDo("ToDo_14", LocalDateTime.now(), user8, new ArrayList<>());
        toDoService.addTodo(todo, user8);
        assertThrows(ToDoNotFound.class, ()->toDoService.getByUserTitle(user8, "ToDo"));
    }

    @Test
    void getAllSuccess() throws UserNotFoundException {
        assertTrue(!userService.getAll().isEmpty());
    }
}
