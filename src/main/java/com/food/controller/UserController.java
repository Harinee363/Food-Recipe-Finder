package com.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.food.entity.UserEntity;
import com.food.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public UserEntity register(@RequestBody UserEntity entity) throws Exception {
        return service.registerEmail(entity);
    }
}
