package com.softserve.itacademy.service;

import java.util.List;

import com.softserve.itacademy.Exceptions.UserNotFoundException;
import com.softserve.itacademy.model.User;

public interface UserService {
    
    User addUser(User user);

    User updateUser(User user) throws UserNotFoundException;

    void deleteUser(User user);

    List<User> getAll();

    User validateUser(User user) throws UserNotFoundException;

}
