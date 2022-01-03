package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Test
    @Transactional
    public void createUserTest(){
        Role role = roleRepository.getOne(2L);
        User validUser  = new User();
        validUser.setEmail("valid2@cv.edu.ua");
        validUser.setFirstName("Valid-Name");
        validUser.setLastName("Valid-Name");
        validUser.setPassword("qwQW12!@");
        validUser.setRole(role);
        validUser = userRepository.save(validUser);
        assertEquals(validUser.getId(), 13);
    }

    @Test
    public void exceptionWhenCreateNonUniqueUserEmailTest(){
        Role role = roleRepository.getOne(2L);
        User validUser  = new User();
        validUser.setEmail("user@cv.edu.ua");
        validUser.setFirstName("Name");
        validUser.setLastName("Surname");
        validUser.setPassword("qwQW12!@");
        validUser.setRole(role);
        userRepository.save(validUser);

        validUser = new User();
        validUser.setEmail("user@cv.edu.ua");
        validUser.setFirstName("Newname");
        validUser.setLastName("Newsurname");
        validUser.setPassword("qwQW12!@");
        validUser.setRole(role);

        User finalUser = validUser;
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, ()->{
            userRepository.save(finalUser);
        });
    }
}