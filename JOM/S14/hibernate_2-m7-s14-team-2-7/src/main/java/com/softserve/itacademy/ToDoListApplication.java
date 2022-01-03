package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.StateService;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ToDoListApplication implements CommandLineRunner {

    @Autowired
    TaskService taskService;
    
    @Autowired
    ToDoService toDoService;

    @Autowired
    StateService stateService;

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Running Spring Boot Application");

        System.out.println(taskService.getByTodoId(7L));
        
        List<ToDo> todos = toDoService.getByUserId(4L);

        for(ToDo toDo : todos)
            System.out.println(toDo);
    }
}

