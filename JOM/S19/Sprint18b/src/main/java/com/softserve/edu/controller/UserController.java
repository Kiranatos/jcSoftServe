package com.softserve.edu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.edu.config.JwtProvider;
import com.softserve.edu.dto.OperationResponce;
import com.softserve.edu.dto.RoleResponce;
import com.softserve.edu.dto.TokenResponse;
import com.softserve.edu.dto.UserRequest;
import com.softserve.edu.dto.UserResponce;
import com.softserve.edu.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtProvider jwtProvider;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public OperationResponce signUp() {
        return new OperationResponce(String.valueOf(true));
    }
    
    @PostMapping("/signup")
    public OperationResponce signUp(
            @RequestParam(value = "login", required = true)
            String login,
            @RequestParam(value = "password", required = true)
            String password) {
        log.info("**/signup userLogin = " + login);
        UserRequest userRequest = new UserRequest(login, password);
        return new OperationResponce(String.valueOf(userService.saveUser(userRequest)));
     }

    @PostMapping("/signin")
    public TokenResponse signIn(
            @RequestParam(value = "login", required = true)
            String login,
            @RequestParam(value = "password", required = true)
            String password) {
        log.info("**/signin userLogin = " + login);
        UserRequest userRequest = new UserRequest(login, password);
        UserResponce userResponce = userService.findByLoginAndPassword(userRequest);
        return new TokenResponse(jwtProvider.generateToken(userResponce.getLogin()));
    }
    
    @GetMapping("/user/date")
    public OperationResponce expirationDate() {
        log.info("**/user/date");
        return new OperationResponce("expiration date is " + userService.getExpirationLocalDate());
    }
    
    @GetMapping("/admin/roles")
    public List<RoleResponce> listRoles() {
        log.info("**/admin/roles");
        return userService.getAllRoles();
    }
}
