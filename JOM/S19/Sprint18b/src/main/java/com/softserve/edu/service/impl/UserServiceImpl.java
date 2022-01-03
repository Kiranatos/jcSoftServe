package com.softserve.edu.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.edu.config.CustomUserDetails;
import com.softserve.edu.dto.RoleResponce;
import com.softserve.edu.dto.UserRequest;
import com.softserve.edu.dto.UserResponce;
import com.softserve.edu.model.Role;
import com.softserve.edu.model.RoleData;
import com.softserve.edu.model.User;
import com.softserve.edu.repository.RoleRepository;
import com.softserve.edu.repository.UserRepository;
import com.softserve.edu.service.UserService;

//@AllArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    // @Autowired
    private UserRepository userRepository;

    // @Autowired
    private RoleRepository roleRepository;

    // @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
            ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        initDefaultData();
    }
    
    private void initDefaultData() {
        if ((roleRepository.count() == 0) 
                && (userRepository.count() == 0)) {
            roleRepository.save(new Role(1, RoleData.USER.toString()));
            Role adminRole = roleRepository.save(new Role(2, RoleData.ADMIN.toString()));
            User admin = new User(1, "admin", passwordEncoder.encode("admin"), adminRole);
            userRepository.save(admin);
        }
    }
    
    public boolean saveUser(UserRequest userRequest) {
        User user = new User();
        user.setRole(roleRepository.findByName(RoleData.USER.toString()));
        user.setLogin(userRequest.getLogin());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return (userRepository.save(user) != null);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
    
    public UserResponce findByLoginAndPassword(UserRequest userRequest) {
        UserResponce result = null;
        User user = userRepository.findByLogin(userRequest.getLogin());
        if ((user != null) 
                && (passwordEncoder.matches(userRequest.getPassword(),
                        user.getPassword()))) {
            result = new UserResponce();
            result.setLogin(userRequest.getLogin());
            result.setRolename(user.getRole().getName());
        }
        return result;
    }
    
    public String getExpirationLocalDate() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDateTime localDate = customUserDetails.getExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy 'at' hh:mm");
        return localDate.format(formatter);
    }
 
    public List<RoleResponce> getAllRoles() {
        List<RoleResponce> result = new ArrayList<>();
        for (Role role : roleRepository.findAll()) {
            result.add(new RoleResponce(role.getName()));
        }
        return result;
    }
    
}
