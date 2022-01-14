package com.socialnetwork.socialnetwork.controller;

import com.socialnetwork.socialnetwork.model.*;
import com.socialnetwork.socialnetwork.service.UserPostService;
import com.socialnetwork.socialnetwork.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userpost")
public class UserPostResource {
    private final UserPostService userPostService;

    public UserPostResource(UserPostService userPostService) {
        this.userPostService = userPostService;
    }

    @GetMapping
    public ResponseEntity<List<UserPost>> getAllUserPosts() {
        List<UserPost> userPostList = this.userPostService.findAllUserPosts();
        return new ResponseEntity<>(userPostList, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Post>> findPostsByUsername(HttpServletRequest request, @PathVariable String username) {
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Post> postList = this.userPostService.findUserPostByUsername(username);
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Post>> getUserPostByUsername(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = JwtUtils.getUsernameFromJwtToken(jwtToken);
        List<Post> postList = this.userPostService.findUserPostByUsername(username);
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @PostMapping("/newPost")
    public ResponseEntity<UserPost> newPost(HttpServletRequest request, @RequestBody Post post) throws Exception {
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = JwtUtils.getUsernameFromJwtToken(jwtToken);
        UserPost newUserPost = this.userPostService.addPost(username, post);
        return new ResponseEntity<>(newUserPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/deletePost/{postID}")
    public ResponseEntity<?> deletePostById(HttpServletRequest request, @PathVariable int postID) {
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = JwtUtils.getUsernameFromJwtToken(jwtToken);
        this.userPostService.deletePostById(username, postID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllPosts")
    public ResponseEntity<?> deleteAllUserPosts(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = JwtUtils.getUsernameFromJwtToken(jwtToken);
        this.userPostService.deleteAllPosts(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/firstPost/{username}")
    public ResponseEntity<Post> findFirstPostOfUser(@PathVariable String username) {
        Post lastPost = this.userPostService.findFirstPostByUsername(username);
        return new ResponseEntity<>(lastPost, HttpStatus.OK);
    }

    @PutMapping("/updateUsername")
    public ResponseEntity upadeUsername(HttpServletRequest request, @RequestBody String newUsername) {
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = JwtUtils.getUsernameFromJwtToken(jwtToken);
        this.userPostService.updateUsername(username, newUsername);
        return new ResponseEntity(HttpStatus.OK);
    }
}
