package com.socialnetwork.socialnetwork.repository;

import com.socialnetwork.socialnetwork.model.UserPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserPostRepository extends MongoRepository<UserPost, String> {
    Optional<UserPost> findUserPostById(String id);

    UserPost findUserPostByUsername(String username);

    void deleteUserPostByUsername(String username);
}
