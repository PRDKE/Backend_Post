package com.socialnetwork.socialnetwork.controller;

import com.socialnetwork.socialnetwork.model.FollowRelationData;
import com.socialnetwork.socialnetwork.service.FollowRelationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relation")
public class FollowRelationResource {
    private final FollowRelationService followRelationService;

    public FollowRelationResource(FollowRelationService followRelationService) {
        this.followRelationService = followRelationService;
    }

    @GetMapping("/{username}/follows")
    public ResponseEntity<List<FollowRelationData>> getAllUserFollows(@PathVariable("username") String username) {
        List<FollowRelationData> newList = followRelationService.getAllIFollow(username);
        return new ResponseEntity<>(newList, HttpStatus.OK);
    }

    @GetMapping("/follow/{username}")
    public ResponseEntity<List<FollowRelationData>> getAllFollowUser(@PathVariable("username") String username) {
        List<FollowRelationData> newList = followRelationService.getAllFollowMe(username);
        return new ResponseEntity<>(newList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<FollowRelationData> addFollowRelationData(@RequestBody String username) {
        FollowRelationData newFollowRelationData = followRelationService.addFollowRelationData(username);
        return new ResponseEntity<>(newFollowRelationData, HttpStatus.CREATED);
    }

    @PostMapping("/{firstUser}/follow/{secondUser}")
    public ResponseEntity addFollowRelation(@PathVariable("firstUser") String firstUser, @PathVariable("secondUser") String secondUser) {
        this.followRelationService.addFollowRelation(firstUser, secondUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
