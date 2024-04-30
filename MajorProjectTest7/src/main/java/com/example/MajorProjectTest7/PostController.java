package com.example.MajorProjectTest7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/post")
    public ResponseEntity<?> getPost(@RequestParam("postID") Long postID) {
        Optional<Post> postOptional = postRepository.findById(postID);
        if (postOptional.isEmpty()) {
           ErrorClass E = new ErrorClass("Post does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(E);
        }

        Post post = postOptional.get();
        return ResponseEntity.ok(post.generateResponseObject());
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        Optional<User> userOptional = userRepository.findById(postRequest.getUserID());
        if (userOptional.isEmpty()) {
            ErrorClass E = new ErrorClass("User does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(E);
        }

        User user = userOptional.get();
        Post post = new Post(postRequest.getPostBody(), user);
        postRepository.save(post);

        return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
    }

    @PatchMapping("/post")
    public ResponseEntity<?> editPost(@RequestBody PostRequest postRequest) {
        Optional<Post> postOptional = postRepository.findById(postRequest.getPostID());
        if (postOptional.isEmpty()) {
            ErrorClass E = new ErrorClass("Post does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(E);
        }

        Post post = postOptional.get();
        post.setPostBody(postRequest.getPostBody());
        postRepository.save(post);

        return ResponseEntity.ok("Post edited successfully");
    }

    @DeleteMapping("/post")
    public ResponseEntity<?> deletePost(@RequestParam("postID") Long postID) {
        Optional<Post> postOptional = postRepository.findById(postID);
        if (postOptional.isEmpty()) {
            ErrorClass E = new ErrorClass("Post does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(E);
        }

        postRepository.deleteById(postID);
        return ResponseEntity.ok("Post deleted");
    }

    static class PostRequest {
        private String postBody;
        private Long userID;
        private Long postID;

        // Getters and setters
        public String getPostBody() {
            return postBody;
        }

        public void setPostBody(String postBody) {
            this.postBody = postBody;
        }

        public Long getUserID() {
            return userID;
        }

        public void setUserID(Long userID) {
            this.userID = userID;
        }

        public Long getPostID() {
            return postID;
        }

        public void setPostID(Long postID) {
            this.postID = postID;
        }
    }
}
