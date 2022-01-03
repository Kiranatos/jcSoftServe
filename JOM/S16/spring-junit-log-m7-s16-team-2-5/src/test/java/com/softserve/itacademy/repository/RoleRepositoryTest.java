package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@AutoConfigureJdbc
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Transactional
    public void newRoleTest() {
        Role role = new Role();
        role.setName("Test");
        Role expected = roleRepository.save(role);
        Role actual = roleRepository.findById(expected.getId()).orElseGet(null);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAll() {
        List<Role> before = roleRepository.findAll();
        Role role = new Role();
        role.setName("Test");
        roleRepository.save(role);
        List<Role> after = roleRepository.findAll();
        Assertions.assertEquals(before.size() + 1, after.size());
    }
}