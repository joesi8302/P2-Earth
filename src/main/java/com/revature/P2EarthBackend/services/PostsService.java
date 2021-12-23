package com.revature.P2EarthBackend.services;

import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.repository.PostsDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PostsService {
    private PostsDao postsDao;

    @Autowired
    public PostsService(PostsDao postsDao){
        this.postsDao = postsDao;
    }

    public List<Posts> getAllPosts(){
        return this.postsDao.findAll();
    }

    public Posts getOnePost(Integer post_id){
        return this.postsDao.getById(post_id);
    }

    public Posts createPost(Posts post){
        return this.postsDao.save(post);

    }

    public Boolean deletePost(Integer post_id){
        return null;
    }
}
