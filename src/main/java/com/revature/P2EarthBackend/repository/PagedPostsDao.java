package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Posts;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagedPostsDao extends PagingAndSortingRepository<Posts,Integer> {
}
