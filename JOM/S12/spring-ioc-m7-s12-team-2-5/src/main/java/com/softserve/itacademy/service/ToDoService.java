package com.softserve.itacademy.service;

import java.util.List;

import com.softserve.itacademy.Exceptions.UserNotFoundException;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.Exceptions.ToDoNotFound;

public interface ToDoService {
    
    ToDo addTodo(ToDo todo, User user) throws UserNotFoundException;

    ToDo updateTodo(ToDo todo) throws ToDoNotFound, UserNotFoundException;

    void deleteTodo(ToDo todo) throws ToDoNotFound;

    List<ToDo> getAll();

    List<ToDo> getByUser(User user) throws UserNotFoundException;

    ToDo getByUserTitle(User user, String title) throws ToDoNotFound, UserNotFoundException;
    
}
