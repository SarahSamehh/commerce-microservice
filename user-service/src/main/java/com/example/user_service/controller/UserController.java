package com.example.user_service.controller;

import com.example.user_service.dto.CreateUserRequest;
import com.example.user_service.entity.User;
import com.example.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User>  createUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

}
