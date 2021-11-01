package com.socialnetwork.socialnetwork.service;

import com.socialnetwork.socialnetwork.model.FollowRelationData;
import com.socialnetwork.socialnetwork.repository.FollowRelationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowRelationService {
    private final FollowRelationRepository followRelationRepository;

    public FollowRelationService(FollowRelationRepository followRelationRepository) {
        this.followRelationRepository = followRelationRepository;
    }

    public FollowRelationData addFollowRelationData(String username){
        return this.followRelationRepository.addFollowRelationData(username);
    }

    public List<FollowRelationData> getAllIFollow(String username) {
        return this.followRelationRepository.getAllIFollow(username);
    }

    public List<FollowRelationData> getAllFollowMe(String username) {
        return this.followRelationRepository.getAllFollowMe(username);
    }

    public void addFollowRelation(String firstUser, String secondUser){
        this.followRelationRepository.addFollowRelation(firstUser, secondUser);
    }
}
