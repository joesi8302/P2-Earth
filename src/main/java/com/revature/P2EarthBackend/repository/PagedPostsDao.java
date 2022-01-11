package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface PagedPostsDao extends PagingAndSortingRepository<Posts, Integer> {
//    Page<Posts> findByUserId(Users user, Pageable paging);

    //todo: add pagable slices by user id
    Slice<Posts> findByUser_userId(Integer userId, Pageable pageable);

}
