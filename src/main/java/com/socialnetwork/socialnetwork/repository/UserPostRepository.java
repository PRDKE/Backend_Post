package com.socialnetwork.socialnetwork.repository;

import com.socialnetwork.socialnetwork.model.UserPost;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserPostRepository extends MongoRepository<UserPost, Long> {
    Optional<UserPost> findUserPostById(Long id);

    Optional<List<UserPost>> findUserPostByUsername(String username);

    void deleteUserPostById(Long id);
}
