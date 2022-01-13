package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Posts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagedPostsDao extends PagingAndSortingRepository<Posts, Integer> {

    Slice<Posts> findByUser_userId(Integer userId, Pageable pageable);

}
