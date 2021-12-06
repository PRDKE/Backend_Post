package com.socialnetwork.socialnetwork.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
public class UserPost {
    @Id
    @GeneratedValue
    private String id;
    private String username;
    private List<Post> postList;

    public UserPost() { }

    public UserPost(String username) {
        this.username = username;
        this.postList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }
}
