package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagedPostsDao extends PagingAndSortingRepository<Posts,Integer> {

    @Query("SELECT COUNT(p.post_id) from Posts AS p")
    Integer findNumberofPosts();

    @Query("SELECT COUNT(p.post_id) from Posts AS p where user_user_id = :user_user_id")
    Integer findNumberofPostsByAccount(@Param("user_user_id") Integer user_user_id);

    @Query("from Posts where user_user_id = :user_user_id")
    Page<Posts> findAllByUserId(@Param("user_user_id") Integer user_user_id, Pageable paging);

}
