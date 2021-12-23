package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsDao extends JpaRepository<Posts, Integer> {
}
