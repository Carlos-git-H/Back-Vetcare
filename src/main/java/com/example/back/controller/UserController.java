package com.example.back.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.back.entity.User;



@RestController
@RequestMapping(path = "api/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<User> getAll() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public Optional<User> getById(@PathVariable("userId") Long userId){
        return userService.getUser(userId);
    }

    @PostMapping
    public void saveUpdate(@RequestBody User user){
        userService.saveOrUpdate(user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Long userId){
        userService.delete(userId);
    }

}
