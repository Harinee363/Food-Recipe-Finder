package com.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.food.entity.UserEntity;
import com.food.service.UserService;

@Controller
@RequestMapping("/users")
public class FormUserController {

    @Autowired
    UserService service;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserEntity entity) throws Exception {
        service.registerEmail(entity);  // Assuming this registers the user
        return "redirect:/users/login";  // Redirect to login page after registration
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login";  // This is the login JSP
    }

   
}
