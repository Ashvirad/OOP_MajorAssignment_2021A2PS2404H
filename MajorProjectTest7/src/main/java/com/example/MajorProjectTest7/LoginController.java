package com.example.MajorProjectTest7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Check if the user exists
        User user = userRepository.findByEmail(email);
        if (user == null) {
            ErrorClass E = new ErrorClass("User does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(E);
        }

        // Check if the password is correct
        if (!user.getPassword().equals(password)) {
            ErrorClass E = new ErrorClass("Username/Password Incorrect");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(E);
        }

        // If user exists and password is correct, login successful
        return ResponseEntity.ok("Login Successful");
    }
}

class LoginRequest {
    private String email;
    private String password;

    public LoginRequest() {}

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
