package com.example.MajorProjectTest7;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class UserFeedController {

    @Autowired
    private PostRepository postRepository;

    // Retrieve all posts by all users in reverse chronological order
    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No posts found");
        }

        // Sort posts by date in reverse order (latest at the top)
        posts.sort((p1, p2) -> p2.getDate().compareTo(p1.getDate()));

        // Map posts to response format
        List<Object> response = posts.stream().map(post -> new Object() {
            public Long postID = post.getPostID();
            public String postBody = post.getPostBody();
            public String date = post.getDate().toString();
            public List<Object> comments = post.getComments().stream().map(comment -> new Object() {
                public Long commentID = comment.getCommentID();
                public String commentBody = comment.getCommentBody();
                public Object commentCreator = new Object() {
                    public Long userID = comment.getUser().getUserID();
                    public String name = comment.getUser().getName();
                };
            }).collect(Collectors.toList());
        }).collect(Collectors.toList());

        return ResponseEntity.ok(new Object() {
            public List<Object> posts = response;
        });
    }
}
