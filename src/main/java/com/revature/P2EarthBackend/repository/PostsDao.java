package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PostsDao extends JpaRepository<Posts, Integer> {

    @Query("from Posts where user_user_id = :user_user_id")
    List<Posts> findAllByUserId(@Param("user_user_id") Long user_id);


    @Query("from Posts where post_id= :post_id")
    Posts findAllByPostId(@Param("post_id")  Long post_id);




}
