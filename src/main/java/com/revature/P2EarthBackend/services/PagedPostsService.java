package com.revature.P2EarthBackend.services;


import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.repository.PagedPostsDao;
import com.revature.P2EarthBackend.repository.PostsDao;
import com.revature.P2EarthBackend.repository.UsersDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class PagedPostsService {
    private PagedPostsDao pagedPostsDao;
    private UsersDao usersDao;
    Logger logger = Logger.getLogger(PostsService.class);

    @Autowired
    public PagedPostsService(PagedPostsDao pagedPostsDao,UsersDao usersDao){
        this.pagedPostsDao = pagedPostsDao;
        this.usersDao = usersDao;
    }

    public List<Posts> getAllPagedPosts(Integer pageNo){

        Pageable paging = PageRequest.of(pageNo, 20);

        Page<Posts> postsList = this.pagedPostsDao.findAll(paging);

        if (postsList == null) return null;

        return postsList.toList();
    }
public List<Posts> getAllPagedPostsByUserId(Integer pageNo,Integer user_id){

    Pageable paging = PageRequest.of(pageNo, 20);

    Slice<Posts> postsList = this.pagedPostsDao.findByUser_userId(user_id, paging);

    return postsList.toList();
}



}
