package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.softserve.itacademy.Exceptions.UserNotFoundException;
import com.softserve.itacademy.model.ToDo;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private List<User> users;

    public UserServiceImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User addUser(User user) {
        for (User u : users) {
            if (u.getEmail().equals(user.getEmail())) {
                throw new IllegalArgumentException("User " + user.toString() + " already exist!");
            }
        }
        users.add(user);
        return user;
    }

    @Override
    public User updateUser(User user)  throws UserNotFoundException{
        for (User u : users) {
            if (u.getEmail().equals(user.getEmail())) {
                u.setFirstName(user.getFirstName());
                u.setLastName(user.getLastName());
                u.setPassword(user.getPassword());
                u.setMyTodos(user.getMyTodos());
                return u;
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    public User validateUser(User user) throws UserNotFoundException {
        return users.stream()
                .filter(user1 -> user1.equals(user))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

}
