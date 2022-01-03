package com.softserve.itacademy.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
        validTask.setToDo(validToDo);

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
        task.setToDo(validToDo);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        System.out.printf("\n\n [%s : %s : %s] \n\n",input,errorValue, violations.toString());
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidNameTask(){
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("  ", "  "),
                Arguments.of("Jo", "Jo")                
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
        assertEquals(validTask.toString(), "Task{id=0, name='Task', priority=LOW, state=State{id=0, name='Done'}, " +
                "toDo=ToDo{id=0, title='Main To Do list', owner=User{id=0, firstName='null', lastName='null', email='null', " +
                "password='null', role=null}}}");
    }

}
