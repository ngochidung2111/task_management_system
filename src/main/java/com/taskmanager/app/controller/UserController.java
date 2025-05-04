package com.taskmanager.app.controller;

import com.taskmanager.app.Response.ApiResponse;
import com.taskmanager.app.model.Task;
import com.taskmanager.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.taskmanager.app.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("")
    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ApiResponse<>(200,"Retrieve all user ok", users);
    }
    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return new ApiResponse<>(200, "User retrieved successfully", user);
        } else {
            return new ApiResponse<>(200, "User not found", null);
        }
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(Authentication auth){
        Long myId = (Long) auth.getDetails();    // the userId from JWT
        User users = userService.getUserById(myId);
        return ResponseEntity.ok(users);
    }

}
