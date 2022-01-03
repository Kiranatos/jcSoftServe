package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.ToDo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureJdbc
public class ToDoRepositoryTest {
    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private TestEntityManager entityManager;

    private ToDo testToDo;
    private List<ToDo> toDos;

    @BeforeEach
    public void init() {
        testToDo = new ToDo();
        testToDo.setTitle("Test");
        testToDo.setCreatedAt(LocalDateTime.now());
        toDos = toDoRepository.findAll();
    }

    @Test
    @Transactional
    public void newTodoTest() {
        ToDo expected = toDoRepository.save(testToDo);
        ToDo actual = toDoRepository.findById(expected.getId()).orElseGet(null);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAll() {
        List<ToDo> allToDos = toDoRepository.findAll();
        Assertions.assertEquals(toDos.size(), allToDos.size());
    }

}