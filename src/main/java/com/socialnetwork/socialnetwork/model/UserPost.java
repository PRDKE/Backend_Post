package com.socialnetwork.socialnetwork.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class UserPost {
    @Id
    private Long id;
    private String username;
    private String imageUrl;
    private String message;
    private LocalDateTime localDateTime;

    public UserPost() { }

    public UserPost(Long id, String username, String imageUrl, String message, LocalDateTime localDateTime) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
        this.message = message;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage(){
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
