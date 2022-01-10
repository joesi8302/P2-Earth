package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagedPostsDao extends PagingAndSortingRepository<Posts,Integer> {
//    Page<Posts> findByUserId(Users user, Pageable paging);



}
