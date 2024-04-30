package com.example.MajorProjectTest7;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestParam("userID") Long userId) {
        // Find user by ID
        User user = userRepository.findById(userId).orElse(null);

        // If user does not exist, return appropriate response
        if (user == null) {
            ErrorClass E = new ErrorClass("User does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(E);
        }

        // Create custom response object
        UserDetailsResponse userDetails = new UserDetailsResponse(user.getName(), user.getUserID(), user.getEmail());

        // Return custom response
        return ResponseEntity.ok(userDetails);
    }
}

class UserDetailsResponse {
    private String name;
    private Long userID;
    private String email;

    // Constructor
    public UserDetailsResponse(String name, Long userID, String email) {
        this.name = name;
        this.userID = userID;
        this.email = email;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
