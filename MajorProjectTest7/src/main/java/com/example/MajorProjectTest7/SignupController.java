package com.example.MajorProjectTest7;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        String email = signupRequest.getEmail();

        // Check if the user already exists
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            ErrorClass E = new ErrorClass("Forbidden, Account already exists");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(E);
        }

        // If user does not exist, create a new user
        User newUser = new User(signupRequest.getName(), signupRequest.getEmail(), signupRequest.getPassword());
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("Account Creation Successful");
    }
}

class SignupRequest {
    private String email;
    private String name;
    private String password;

    public SignupRequest() {}

    public SignupRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

