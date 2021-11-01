package com.socialnetwork.socialnetwork.service;

import com.socialnetwork.socialnetwork.exception.PostNotFoundException;
import com.socialnetwork.socialnetwork.model.UserPost;
import com.socialnetwork.socialnetwork.repository.UserPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPostService {
    private final UserPostRepository userPostRepository;

    @Autowired
    public UserPostService(UserPostRepository userPostRepository) {
        this.userPostRepository = userPostRepository;
    }

    public UserPost addUserPost(UserPost userPost){
        return userPostRepository.save(userPost);
    }

    public List<UserPost> findAllUserPosts() {
        return userPostRepository.findAll();
    }

    public UserPost updateUserPost(UserPost userPost) {
        return userPostRepository.save(userPost);
    }

    public UserPost findUserPostById(Long id) {
        return userPostRepository.findUserPostById(id)
                .orElseThrow(() -> new PostNotFoundException("Post by id " + id + " was not found!"));
    }

    public List<UserPost> findUserPostByUsername(String username) {
        return userPostRepository.findUserPostByUsername(username)
                .orElseThrow(() -> new PostNotFoundException("User by username " + username + " was not found!"));
    }

    public void deleteUserPostById(Long id) {
        userPostRepository.deleteUserPostById(id);
    }
}
