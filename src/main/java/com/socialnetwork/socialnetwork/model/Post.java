package com.socialnetwork.socialnetwork.model;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private String imageUrl;
    private String message;
    private LocalDateTime localDateTime;

    public Post (String imageUrl, String message) {
        this.id = 1;
        this.imageUrl = imageUrl;
        this.message = message;
        this.localDateTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
