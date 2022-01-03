package com.softserve.itacademy.model;


import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SpringBootTest
class RoleTests {

    
    private static Role mentorRole;
    private static User validUser;
    private static List<User> validUsersList;

    @BeforeAll
    static void init(){
        mentorRole = new Role();
        mentorRole.setName("MENTOR");
        validUser  = new User();
        validUser.setEmail("valid@cv.edu.ua");
        validUser.setFirstName("Valid-Name");
        validUser.setLastName("Valid-Name");
        validUser.setPassword("qwQW12!@");
        validUser.setRole(mentorRole);
        validUsersList = Collections.singletonList(validUser);
        mentorRole.setUsers(validUsersList);
    }

    @Test
    void createValidRole() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Role>> violations = validator.validate(mentorRole);
        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidNameRole")
    void constraintViolationInvalidName(String input, String errorValue) {
        Role role = new Role();
        role.setName(input);
        role.setUsers(validUsersList);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidNameRole(){
        return Stream.of(
                Arguments.of("invalid", "invalid"),
                Arguments.of("123", "123"),
                Arguments.of("", "")
        );
    }

    @Test
    void getNameRole() {
        assertEquals(mentorRole.getName(), "MENTOR");
    }

    @Test
    void getUsersRole() {
        assertEquals(mentorRole.getUsers().size(), 1);
    }

    @Test
    void toStringRole() {
        assertEquals(mentorRole.toString(), "Role {id = 0, name = 'MENTOR'}");
    }
}