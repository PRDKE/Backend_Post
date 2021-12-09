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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;

@Service
public class UserPostService {
    private final UserPostRepository userPostRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserPostService(UserPostRepository userPostRepository, MongoTemplate mongoTemplate) {
        this.userPostRepository = userPostRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<UserPost> findAllUserPosts() {
        List<UserPost> userPostList = this.userPostRepository.findAll();
        return userPostList;
    }

    public UserPost addUserPost(String username) {
        return this.userPostRepository.save(new UserPost(username));
    }

    public UserPost addPost(String username, Post post) throws Exception {
        if (ObjectUtils.isEmpty(username)
        || ObjectUtils.isEmpty(post.getImageUrl())
        || ObjectUtils.isEmpty(post.getMessage())) {
            throw new Exception("Bad request detected!");
        } else {
            if (post.getImageUrl().equals("Grinning Face")) {
                post.setImageUrl("http://s3.amazonaws.com/pix.iemoji.com/images/emoji/apple/ios-12/256/grinning-face-with-smiling-eyes.png");
            }
            if (post.getImageUrl().equals("Sad Face")) {
                post.setImageUrl("https://cdn.pixabay.com/photo/2020/08/04/04/14/juneteenth-5461576_960_720.png");
            }
            if (post.getImageUrl().equals("Bored Face")) {
                post.setImageUrl("https://cdn.pixabay.com/photo/2020/12/27/20/24/emoji-5865207_960_720.png");
            }

            UserPost newUserPost = this.userPostRepository.findUserPostByUsername(username);
            List<Post> postList = new ArrayList<>();
            if (newUserPost == null) {
                addUserPost(username);
                post.setId(1);
                postList.add(post);
            } else if (newUserPost != null) {
                postList.addAll(newUserPost.getPostList());
                post.setId(postList.size()-1);
                postList.add(post);
            }

            Query query = new Query();
            query.addCriteria(Criteria.where("username").is(username));

            Update update = new Update();
            update.set("postList", postList);
            return mongoTemplate.findAndModify(query, update, UserPost.class);
        }
    }

    public List<Post> findUserPostByUsername(String username) {
        UserPost userPost = this.userPostRepository.findUserPostByUsername(username);
        if (userPost == null) {
            throw new PostNotFoundException("There is no user with this username!");
        }
        return userPost.getPostList();
    }

    public void deletePostById(String username, int id) {
        UserPost userPost = this.userPostRepository.findUserPostByUsername(username);
        if (userPost == null) {
            throw new PostNotFoundException("This user does not have any posts!");
        }
        for (Post post : userPost.getPostList()) {
            if (post.getId() == id) {
                userPost.getPostList().remove(post);
                break;
            }
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        Update update = new Update();
        update.set("postList", userPost.getPostList());
        mongoTemplate.findAndModify(query, update, UserPost.class);
    }

    public UserPost updatePost(String username, Post post) {
        UserPost userPost = this.userPostRepository.findUserPostByUsername(username);
        if (userPost == null) {
            throw new PostNotFoundException("This user does not have any posts!");
        }
        for (Post postSearch : userPost.getPostList()) {
            if (postSearch.equals(post)) {
                postSearch = post;
            }
        }
        return userPost;
    }

    public void deleteAllPosts(String username) {
        this.userPostRepository.deleteUserPostByUsername(username);
    }
}
