package com.revature.P2EarthBackend.controllers;


import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.services.PagedPostsService;
import com.revature.P2EarthBackend.services.PostsService;
import com.revature.P2EarthBackend.services.UploadService;
import com.revature.P2EarthBackend.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "pagedposts")
public class PagedPostsController {

    private PagedPostsService pagedPostsService;
    private UsersService usersService;
    private UploadService uploadService;

    @Autowired
    public PagedPostsController(PagedPostsService pagedPostsService,UsersService usersService,UploadService uploadService){
        this.pagedPostsService=pagedPostsService;
        this.uploadService=uploadService;
        this.usersService=usersService;

    }

    @GetMapping("/{pageNo}/{pageSize}")
    public List<Posts> getAllPagedPosts(@PathVariable int pageNo,
                                        @PathVariable int pageSize) {
        return pagedPostsService.getAllPagedPosts(pageNo,pageSize);
    }
}
