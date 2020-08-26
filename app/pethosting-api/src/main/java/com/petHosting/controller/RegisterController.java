package com.petHosting.controller;

import com.petHosting.dto.UserDTO;
import com.petHosting.entity.Role;
import com.petHosting.entity.User;
import com.petHosting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin
public class RegisterController {

    @Autowired
    UserService userService;

    public static final String INVALID_INPUT = "Input is not valid";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String SUCCESS = "User created successfully";

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO)
            throws Exception {
        if(!userDTO.getPassword().equals(userDTO.getReTypedPassword()))
            return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(INVALID_INPUT);
        if(userService.emailAlreadyUsed(userDTO.getEmail()))
            return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(USER_ALREADY_EXISTS);
        User createdUser;
        if(userDTO.getRegisterAsGuest())
            createdUser = userService.add(userDTO, Set.of(new Role("Guest")));
        else
            createdUser = userService.add(userDTO, Set.of(new Role("Host")));

        return ResponseEntity.ok(createdUser);
    }
}
