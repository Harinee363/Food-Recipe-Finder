package com.food.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.food.Repository.UserRepository;
import com.food.entity.UserEntity;
import com.food.exception.EmailAlreadyExists;

@Service
public class UserService {

    private final UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserEntity registerEmail(UserEntity entity) throws Exception {
        if (repo.existsByEmail(entity.getEmail())) {
            throw new EmailAlreadyExists("Email Id Already Exists");
        }
        entity.setPassword(encoder.encode(entity.getPassword()));
        return repo.save(entity);
    }

    public UserEntity getByEmail(String email) {
        Optional<UserEntity> user = repo.findByEmail(email);
        return user.orElse(null);
    }
    public UserEntity findByUsername(String username) {
        return repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
