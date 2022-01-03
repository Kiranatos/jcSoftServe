package com.softserve.itacademy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.softserve.itacademy.Exceptions.ToDoNotFound;
import com.softserve.itacademy.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;

@Service
public class ToDoServiceImpl implements ToDoService {

    private UserService userService;

    @Autowired
    public ToDoServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public ToDo addTodo(ToDo todo, User user) throws UserNotFoundException {
        getByUser(userService.validateUser(user)).add(todo);
        return todo;
    }

    public ToDo updateTodo(ToDo todo) throws ToDoNotFound, UserNotFoundException {
    ToDo toDoUpdated = getByUserTitle(todo.getOwner(), todo.getTitle());
    toDoUpdated.getTasks().addAll(todo.getTasks());
        return toDoUpdated;
    }

    public void deleteTodo(ToDo todo) throws ToDoNotFound{
        List<ToDo> list = getAll();
        if(!list.remove(todo)) throw new ToDoNotFound();
    }

    public List<ToDo> getAll() {
        return userService.getAll().stream().map(user -> user.getMyTodos())
                .flatMap(toDos -> toDos.stream()).collect(Collectors.toList());
    }

    public List<ToDo> getByUser(User user) throws UserNotFoundException{
        return userService.validateUser(user).getMyTodos();
    }

    public ToDo getByUserTitle(User user, String title) throws ToDoNotFound, UserNotFoundException {
        return getByUser(userService.validateUser(user)).stream()
                .filter(toDo -> toDo.getTitle().equals(title))
                .findFirst()
                .orElseThrow(ToDoNotFound::new);
    }
}
