package com.example.MajorProjectTest7;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;

    private String postBody;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Temporal(TemporalType.DATE) // Change to DATE
    private LocalDate date;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Post() {
        this.date = LocalDate.now();
    }

    public Post(String postBody, User user) {
        this.postBody = postBody;
        this.user = user;
        this.date = LocalDate.now();
    }

    // Getters and setters

    public Long getPostID() {
        return postID;
    }

    public void setPostId(Long postID) {
        this.postID = postID;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    // Method to generate response object
    public Object generateResponseObject() {
        List<Object> commentsResponse = new ArrayList<>();
        for (Comment comment : comments) {
            Object commentObj = new Object() {
                public Long commentID = comment.getCommentID();
                public String commentBody = comment.getCommentBody();
                public Object commentCreator = new Object() {
                    public Long userID = comment.getUser().getUserID();
                    public String name = comment.getUser().getName();
                };
            };
            commentsResponse.add(commentObj);
        }

        return new Object() {
            public Long postId = getPostID();
            public String postBody = getPostBody();
            public String date = getDate().toString();
            public List<Object> comments = commentsResponse;
        };
    }
}
