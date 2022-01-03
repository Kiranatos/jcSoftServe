package com.softserve.itacademy.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class TaskTests {

    private static Task validTask;
    private static ToDo validToDo;
    private static State validState;

    @BeforeAll
    static void init(){
        validTask = new Task();
        validTask.setName("Task");
        validTask.setPriority(Priority.LOW);

        validToDo = new ToDo();
        validToDo.setTitle("Main To Do list");
        validToDo.setCreatedAt(LocalDateTime.now());
        validToDo.setOwner(new User());
        validTask.setTodo(validToDo);

        validState = new State();
        validState.setName("Done");
        validTask.setState(validState);
    }

    @Test
    void createValidTask() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(validTask);
        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidNameTask")
    void constraintViolationInvalidName(String input, String errorValue) {
        Task task = new Task();
        task.setName(input);
        task.setPriority(Priority.LOW);
        task.setState(validState);
        task.setTodo(validToDo);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidNameTask(){
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("  ", "  "),
                Arguments.of("1", "1")
        );
    }

    @Test
    void getNameTask() {
        assertEquals(validTask.getName(), "Task");
    }

    @Test
    void getPriorityTask() {
        assertEquals(validTask.getPriority(), Priority.LOW);
    }

    @Test
    void getStateTask() {
        assertEquals(validTask.getState().getName(), "Done");
    }

    @Test
    void getToDoTask() {
        assertEquals(validTask.getToDo().getTitle(), "Main To Do list");
    }

    @Test
    void toStringTask() {
        LocalDateTime toDo = validToDo.getCreatedAt();
        assertEquals(validTask.toString(), "Task {id = 0, name = 'Task', priority = LOW, " +
                "todo = ToDo {id = 0, title = 'Main To Do list', createdAt = " + toDo + "} , " +
                "state = State {id = 0, name = 'Done'} } ");
    }
}