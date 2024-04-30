package com.example.MajorProjectTest7;

import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentID;

    private String commentBody;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Comment() {
    }

    public Comment(String commentBody, User user, Post post) {
        this.commentBody = commentBody;
        this.user = user;
        this.post = post;
    }

    // Getters and setters
    public Long getCommentID() {
        return commentID;
    }

    public void setCommentID(Long commentID) {
        this.commentID = commentID;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    // Method to generate response object
    public Object generateResponseObject() {
        if (commentID == null) {
            return "Comment does not exist";
        }

        return new Object() {
            public Long commentID = getCommentID();
            public String commentBody = getCommentBody();
            public Object commentCreator = new Object() {
                public Long userID = getUser().getUserID();
                public String name = getUser().getName();
            };
        };
    }
}
