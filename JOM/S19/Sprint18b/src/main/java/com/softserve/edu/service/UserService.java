package com.softserve.edu.service;

import java.time.LocalDate;
import java.util.List;

import com.softserve.edu.dto.RoleResponce;
import com.softserve.edu.dto.UserRequest;
import com.softserve.edu.dto.UserResponce;
import com.softserve.edu.model.User;

public interface UserService {

    boolean saveUser(UserRequest userRequest);
    
    User findByLogin(String login);

    UserResponce findByLoginAndPassword(UserRequest userRequest);

    String getExpirationLocalDate();
    
    List<RoleResponce> getAllRoles();
    
}
