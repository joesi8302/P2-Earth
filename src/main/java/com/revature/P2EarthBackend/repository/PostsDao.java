package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsDao extends JpaRepository<Posts, Integer> {

    @Query("from Posts where user_id = :user_id")
    List<Posts> findAllByUserId(@Param("user_id") Integer user_id);

}
