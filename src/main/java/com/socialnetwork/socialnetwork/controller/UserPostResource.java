package com.socialnetwork.socialnetwork.controller;

import com.socialnetwork.socialnetwork.model.UserPost;
import com.socialnetwork.socialnetwork.service.UserPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userpost")
public class UserPostResource {
    private final UserPostService userPostService;

    public UserPostResource(UserPostService userPostService) {
        this.userPostService = userPostService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserPost>> getAllUserPost() {
        List<UserPost> userPosts = userPostService.findAllUserPosts();
        return new ResponseEntity<>(userPosts, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserPost> getUserPostById(@PathVariable("id") Long id) {
        UserPost userPost = userPostService.findUserPostById(id);
        return new ResponseEntity<>(userPost, HttpStatus.OK);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<List<UserPost>> getUserPostByUsername(@PathVariable("username") String username) {
        List<UserPost> userPosts = userPostService.findUserPostByUsername(username);
        return new ResponseEntity<>(userPosts, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<UserPost> addUserPost(@RequestBody UserPost userPost) {
        UserPost newUserPost = userPostService.addUserPost(userPost);
        return new ResponseEntity<>(newUserPost, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserPost> updateUserPost(@RequestBody UserPost userPost) {
        UserPost newUserPost = userPostService.updateUserPost(userPost);
        return new ResponseEntity<>(newUserPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserPost(@PathVariable("id") Long id) {
        userPostService.deleteUserPostById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
