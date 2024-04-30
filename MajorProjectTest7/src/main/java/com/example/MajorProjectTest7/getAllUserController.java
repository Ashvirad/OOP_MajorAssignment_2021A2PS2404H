package com.example.MajorProjectTest7;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class getAllUserController {

    @Autowired
    private UserRepository userRepository;

    // Retrieve all users
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
        }

        return ResponseEntity.ok(users.stream().map(user -> new Object() {
            public String name = user.getName();
            public Long userID = user.getUserID();
            public String email = user.getEmail();
        }));
    }
}
