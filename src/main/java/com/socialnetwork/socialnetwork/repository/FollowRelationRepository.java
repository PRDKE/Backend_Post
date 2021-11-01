package com.socialnetwork.socialnetwork.repository;

import com.socialnetwork.socialnetwork.model.FollowRelationData;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRelationRepository extends Neo4jRepository<FollowRelationData, Long> {

    @Query("MATCH (a:FollowRelationData {username:$username})-[r:FOLLOWS]->(b:FollowRelationData) RETURN b")
    List<FollowRelationData> getAllIFollow(@Param("username") String username);

    @Query("MATCH (a:FollowRelationData)-[r:FOLLOWS]->(b:FollowRelationData {username:$username}) RETURN a")
    List<FollowRelationData> getAllFollowMe(String username);

    @Query("MATCH (a:FollowRelationData {username:$firstUser}), (b:FollowRelationData {username:$secondUser}) MERGE (a)-[f:FOLLOWS]->(b)")
    void addFollowRelation(@Param("firstUser") String firstUser, @Param("secondUser") String secondUser);

    @Query("CREATE (n:FollowRelationData {username:$username})")
    FollowRelationData addFollowRelationData(@Param("username") String username);
}
