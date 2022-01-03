package com.softserve.itacademy.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StateTests {

    private static State validState;
    private static Task validTask;
    private static List<Task> validTasksList;

    @BeforeAll
    static void init(){
        validState = new State();
        validState.setName("In Progress");
        validTask = new Task();
        validTask.setName("Task1");
        validTask.setPriority(Priority.HIGH);
        validTask.setToDo(new ToDo());
        validTask.setState(validState);
        validTasksList = Collections.singletonList(validTask);
        validState.setTasks(validTasksList);
    }

    @Test
    void createValidState() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<State>> violations = validator.validate(validState);
        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidNameState")
    void constraintViolationInvalidName(String input, String errorValue) {
        State state = new State();
        state.setName(input);
        state.setTasks(validTasksList);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<State>> violations = validator.validate(state);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidNameState(){
        return Stream.of(
                Arguments.of("invalid", "invalid"),
                Arguments.of("1234", "1234"),
                Arguments.of("", "")
        );
    }

    @Test
    void getNameState() {
        assertEquals(validState.getName(), "In Progress");
    }

    @Test
    void getTasksState() {
        assertEquals(validState.getTasks().size(), 1);
    }

    @Test
    void toStringState() {
        assertEquals(validState.toString(), "State{id=0, name='In Progress'}");
    }
}