package com.revature.P2EarthBackend.services;


import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.repository.PagedPostsDao;
import com.revature.P2EarthBackend.repository.PostsDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagedPostsService {
    private PagedPostsDao pagedPostsDao;
    Logger logger =Logger.getLogger(PostsService.class);

    @Autowired
    public PagedPostsService(PagedPostsDao pagedPostsDao){
        this.pagedPostsDao = pagedPostsDao;
    }

    public List<Posts> getAllPagedPosts(Integer pageNo){

        Pageable paging = PageRequest.of(pageNo, 20);

        Page<Posts> postsList= this.pagedPostsDao.findAll(paging);

        if (postsList == null) return null;

        return postsList.toList();
    }

    public List<Posts> getAllPagedPostsByUser(Integer pageNo, Integer user_id, Integer pageSize){
        Sort sort = Sort.by(Sort.by(), "user_user_id");
        Pageable paging = PageRequest.of(pageNo, 20, sort );

        Page<Posts> postsList= this.pagedPostsDao.findAll(paging);

        if (postsList == null) return null;

        return postsList.toList();
    }





}
