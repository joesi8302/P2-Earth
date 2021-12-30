package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsDao extends JpaRepository<Posts, Integer> {

    @Query("from Posts where user_id = :user_id")
    List<Posts> findAllByUserId(@Param("user_id") Integer user_id);

}
