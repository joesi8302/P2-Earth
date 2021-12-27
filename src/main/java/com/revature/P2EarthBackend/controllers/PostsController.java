package com.revature.P2EarthBackend.controllers;


import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "posts")
public class PostsController {

    private PostsService postsService;

    @Autowired
    public PostsController(PostsService postsService){
        this.postsService = postsService;
    }

    @GetMapping
    public List<Posts> getAllPosts(){
        return postsService.getAllPosts();
    }

    @GetMapping
    public Posts getOnePost(@PathVariable Integer postId){
        return postsService.getOnePost(postId);
    }

    @GetMapping List<Posts> getAllUserPosts(@PathVariable Integer userId){
        return postsService.getAllUserPosts(userId);
    }

    @PutMapping Posts createPost(@RequestBody Posts post){
        return postsService.createPost(post);
    }

}
