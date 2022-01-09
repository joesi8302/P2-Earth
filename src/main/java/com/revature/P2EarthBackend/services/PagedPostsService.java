package com.revature.P2EarthBackend.services;


import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.repository.PagedPostsDao;
import com.revature.P2EarthBackend.repository.PostsDao;
import com.revature.P2EarthBackend.repository.UsersDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class PagedPostsService {
    private PagedPostsDao pagedPostsDao;
    private UsersDao usersDao;
    Logger logger =Logger.getLogger(PostsService.class);

    @Autowired
    public PagedPostsService(PagedPostsDao pagedPostsDao,UsersDao usersDao){
        this.pagedPostsDao = pagedPostsDao;
        this.usersDao=usersDao;
    }

    public List<Posts> getAllPagedPosts(Integer pageNo){

        Pageable paging = PageRequest.of(pageNo, 20);

        Page<Posts> postsList= this.pagedPostsDao.findAll(paging);

        if (postsList == null) return null;

        return postsList.toList();
    }

//    public List<Posts> gettAllPafedPostsByUserId(Integer pageNo,Integer user_ID,Integer pageSize){
//        Users user =usersDao.getById(user_ID);
//
//        Pageable paging=PageRequest.of(pageNo,pageSize);
//
//        Page<Posts> postList=this.pagedPostsDao.findByUserId(user,paging);
//        return postList.toList();
//    }
public List<Posts> getAllPagedPostsByUserId(Integer pageNo,Integer user_id){

    Pageable paging = PageRequest.of(pageNo, 20);

    Page<Posts> postsList= this.pagedPostsDao.findAll(paging);


    List<Posts> postListAllPosts=postsList.toList();
    List<Posts> posts=new ArrayList<Posts>();
    for(Integer i=0;i<postListAllPosts.size();i++){
        if (postListAllPosts.get(i).getUser().getUser_id()==user_id){
            posts.add(postListAllPosts.get(i));
        }
    }

    return posts;
}



}
