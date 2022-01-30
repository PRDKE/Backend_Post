package com.socialnetwork.socialnetwork.service;

import com.socialnetwork.socialnetwork.exception.PostNotFoundException;
import com.socialnetwork.socialnetwork.model.Post;
import com.socialnetwork.socialnetwork.model.UserPost;
import com.socialnetwork.socialnetwork.repository.UserPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserPostService {
    private final UserPostRepository userPostRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserPostService(UserPostRepository userPostRepository, MongoTemplate mongoTemplate) {
        this.userPostRepository = userPostRepository;
        this.mongoTemplate = mongoTemplate;
    }

    // retrieves all UserPost objects
    public List<UserPost> findAllUserPosts() {
        List<UserPost> userPostList = this.userPostRepository.findAll();
        return userPostList;
    }

    // creates a new UserPost object
    public UserPost addUserPost(String username) {
        return this.userPostRepository.save(new UserPost(username));
    }

    // creates a new post
    public UserPost addPost(String username, Post post) throws Exception {
        // check if the given information is empty
        if (ObjectUtils.isEmpty(username)
        || ObjectUtils.isEmpty(post.getImageUrl())
        || ObjectUtils.isEmpty(post.getMessage())) {
            throw new Exception("Bad request detected!");
        } else {
            // set the imageUrl (path is important for angular frontend and the images are saved in the assets folder)
            if (post.getImageUrl().equals("Grinning Face")) {
                post.setImageUrl("/assets/img/grinning_face.png");
            }
            if (post.getImageUrl().equals("Sad Face")) {
                post.setImageUrl("/assets/img/sad_face.png");
            }
            if (post.getImageUrl().equals("Bored Face")) {
                post.setImageUrl("/assets/img/bored_face.png");
            }

            UserPost newUserPost = this.userPostRepository.findUserPostByUsername(username);
            List<Post> postList = new ArrayList<>();
            // check if there is already a document with the current username
            // if the user does not have any prior posts -> add a new UserPost object with the given post
            if (newUserPost == null) {
                addUserPost(username);
                post.setId(0);
                postList.add(post);
            } else if (newUserPost != null) {
                // if the given username already exists in the post-database
                postList.addAll(newUserPost.getPostList());
                int highestID;
                // get the current highest id
                if (postList.isEmpty()) {
                    highestID = 0;
                } else {
                    highestID = postList.stream().map(x -> x.getId()).max(Integer::compare).get();
                }
                // set id of new post
                post.setId(highestID + 1);
                postList.add(post);
            }

            // user mongoTemplate to update the document
            Query query = new Query();
            query.addCriteria(Criteria.where("username").is(username));

            Update update = new Update();
            update.set("postList", postList);
            return mongoTemplate.findAndModify(query, update, UserPost.class);
        }
    }

    // retrieve a userPost object with the given username
    // throw exception if there is no matching username in the database
    public List<Post> findUserPostByUsername(String username) {
        UserPost userPost = this.userPostRepository.findUserPostByUsername(username);
        if (userPost == null) {
            throw new PostNotFoundException("There is no user with this username!");
        }
        // return all post of the user in descending order (by data and time)
        return userPost.getPostList().stream().sorted(Comparator.comparing(Post::getLocalDateTime).reversed()).collect(Collectors.toList());
    }

    // retrieve the last post of a user with a matching username
    public Post findFirstPostByUsername(String username) {
        List<Post> newList = this.findUserPostByUsername(username);
        return newList.get(0);
    }

    // delete a post
    public void deletePostById(String username, int id) {
        UserPost userPost = this.userPostRepository.findUserPostByUsername(username);
        // check if there is a userPost object with a matching username
        if (userPost == null) {
            throw new PostNotFoundException("This user does not have any posts!");
        }
        // retrieve the post with the given id and delete it
        for (Post post : userPost.getPostList()) {
            if (post.getId() == id) {
                userPost.getPostList().remove(post);
                break;
            }
        }
        // user mongoTemplate to delete the post (update the whole postList)
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        Update update = new Update();
        update.set("postList", userPost.getPostList());
        mongoTemplate.findAndModify(query, update, UserPost.class);
    }

//    public UserPost updatePost(String username, Post post) {
//        UserPost userPost = this.userPostRepository.findUserPostByUsername(username);
//        if (userPost == null) {
//            throw new PostNotFoundException("This user does not have any posts!");
//        }
//        for (Post postSearch : userPost.getPostList()) {
//            if (postSearch.equals(post)) {
//                postSearch = post;
//            }
//        }
//        return userPost;
//    }

    // update a username of a userPost object
    public void updateUsername(String currentUsername, String newUsername) {
        // search for a mongoDB document with a matching username
        UserPost userPost = this.userPostRepository.findUserPostByUsername(currentUsername);
        if (userPost == null) {
            throw new PostNotFoundException("This user does not have any posts!");
        }
        // update the username of a mongoDB document witch the use of mongoTemplate
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(currentUsername));
        Update update = new Update();
        update.set("username", newUsername);
        mongoTemplate.findAndModify(query, update, UserPost.class);
    }

    // delete all Posts of the database
    // this method was only created for test purposes
    public void deleteAllPosts(String username) {
        this.userPostRepository.deleteUserPostByUsername(username);
    }
}
