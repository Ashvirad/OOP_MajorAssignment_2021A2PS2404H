package com.example.MajorProjectTest7;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new comment
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest) {
        Optional<Post> postOptional = postRepository.findById(commentRequest.getPostID());
        if (postOptional.isEmpty()) {
            ErrorClass E = new ErrorClass("Post does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(E);
        }

        Optional<User> userOptional = userRepository.findById(commentRequest.getUserID());
        if (userOptional.isEmpty()) {
            ErrorClass E = new ErrorClass("User does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(E);
        }

        Comment comment = new Comment(commentRequest.getCommentBody(), userOptional.get(), postOptional.get());
        commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully");
    }

    // Retrieve an existing comment
    @GetMapping
    public ResponseEntity<?> getComment(@RequestParam("commentID") Long commentID) {
        Optional<Comment> commentOptional = commentRepository.findById(commentID);
        if (commentOptional.isEmpty()) {
            ErrorClass E = new ErrorClass("Comment does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(E);
        }

        Comment comment = commentOptional.get();
        return ResponseEntity.ok(comment.generateResponseObject());
    }

    // Edit an existing comment
    @PatchMapping
    public ResponseEntity<?> editComment(@RequestBody CommentRequest commentRequest) {
        Optional<Comment> commentOptional = commentRepository.findById(commentRequest.getCommentID());
        if (commentOptional.isEmpty()) {
            ErrorClass E = new ErrorClass("Comment does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(E);
        }

        Comment comment = commentOptional.get();
        comment.setCommentBody(commentRequest.getCommentBody());
        commentRepository.save(comment);

        return ResponseEntity.ok("Comment edited successfully");
    }

    // Delete an existing comment
    @DeleteMapping
    public ResponseEntity<?> deleteComment(@RequestParam("commentID") Long commentID) {
        Optional<Comment> commentOptional = commentRepository.findById(commentID);
        if (commentOptional.isEmpty()) {
            ErrorClass E = new ErrorClass("Comment does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(E);
        }

        commentRepository.deleteById(commentID);
        return ResponseEntity.ok("Comment deleted");
    }
}

class CommentRequest {
    private String commentBody;
    private Long postID;
    private Long userID;
    private Long commentID;

    public CommentRequest() {}

    public CommentRequest(String commentBody, Long postID, Long userID, Long commentID) {
        this.commentBody = commentBody;
        this.postID = postID;
        this.userID = userID;
        this.commentID = commentID;
    }

    // Getters and setters
    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public Long getPostID() {
        return postID;
    }

    public void setPostId(Long postID) {
        this.postID = postID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserId(Long userID) {
        this.userID = userID;
    }

    public Long getCommentID() {
        return commentID;
    }

    public void setCommentId(Long commentID) {
        this.commentID = commentID;
    }
}

