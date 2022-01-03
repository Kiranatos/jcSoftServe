package com.softserve.itacademy.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class ToDoTests {

    private static ToDo validToDo;
    private static User validUser;
    private static List<Task> validTasksList;
    private static Task validTask;

    @BeforeAll
    static void init() {
        validToDo = new ToDo();
        validToDo.setTitle("Main To Do list");
        validToDo.setCreatedAt(LocalDateTime.now());

        validUser = new User();
        validUser.setEmail("valid@cv.edu.ua");
        validUser.setFirstName("Valid-Name");
        validUser.setLastName("Valid-Name");
        validUser.setPassword("qwQW12!@");
        validUser.setRole(new Role());
        validToDo.setOwner(validUser);

        validTasksList = Collections.singletonList(validTask);
        validToDo.setTasks(validTasksList);
    }

    @Test
    void createValidTodo() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(validToDo);
        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidTitleTodo")
    void constraintViolationInvalidTitle(String input, String errorValue) {
        ToDo todo = new ToDo();
        todo.setTitle(input);
        todo.setCreatedAt(LocalDateTime.now());
        todo.setOwner(new User());
        validToDo.setTasks(validTasksList);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(todo);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidTitleTodo() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("  ", "  "),
                Arguments.of(null, null)
        );
    }

    @Test
    void getTitleToDo() {
        assertEquals(validToDo.getTitle(), "Main To Do list");
    }

    @Test
    void getOwnerToDo() {
        assertEquals(validToDo.getOwner().getFirstName(), "Valid-Name");
    }

    @Test
    void getTasksToDo() {
        assertEquals(validToDo.getTasks().size(), 1);
    }

    @Test
    void toStringToDo() {
        LocalDateTime toDo = validToDo.getCreatedAt();
        assertEquals(validToDo.toString(), "ToDo {id = 0, title = 'Main To Do list', createdAt = " + toDo + "} ");
    }
}
