package com.softserve.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softserve.edu.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);
}
