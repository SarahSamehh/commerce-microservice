package com.example.user_service.service;

import com.example.user_service.dto.CreateUserRequest;
import com.example.user_service.entity.User;
import com.example.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest request){
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
