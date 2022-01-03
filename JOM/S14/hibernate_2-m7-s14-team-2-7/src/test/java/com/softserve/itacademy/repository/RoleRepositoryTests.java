package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class RoleRepositoryTests {

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void createRole() {
        Role role = new Role();
        role.setName("NEW");
        role = roleRepository.save(role);
        assertEquals(11, role.getId());
    }

    @Test
    public void exceptionWhenCreateNonUniqueRoleTest() {
        Role role = new Role();
        role.setName("ADMIN");

        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            roleRepository.save(role);
        });
    }

}
