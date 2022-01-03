package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Transactional
    public void newUserTest() {
        Role role = roleRepository.getOne(2L);
        User user = new User();
        user.setFirstName("Sara");
        user.setLastName("Black");
        user.setEmail("sara@gmail.com");
        user.setPassword("sara123@!");
        user.setRole(role);
        userRepository.save(user);
        User actual = userRepository.getUserByEmail("sara@gmail.com");
        Assertions.assertEquals("sara@gmail.com", actual.getEmail());
    }
}
