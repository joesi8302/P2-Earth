package com.revature.P2EarthBackend.controllers;


import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.models.ResponseDTO;
import com.revature.P2EarthBackend.services.PagedPostsService;
import com.revature.P2EarthBackend.services.PostsService;
import com.revature.P2EarthBackend.services.UploadService;
import com.revature.P2EarthBackend.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{pageNo}")
    public ResponseEntity<ResponseDTO> getAllPagedPosts(@PathVariable int pageNo) {
        List<Posts> listfromDB = pagedPostsService.getAllPagedPosts(pageNo);

        if (listfromDB == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Cannot fetch paged posts"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(listfromDB, "Successfully retrieved all paged posts"));
    }

    @GetMapping("/{pageNo}/{user_id}")
    public ResponseEntity<ResponseDTO> getAllPagedPostsByUserId(@PathVariable Integer pageNo,@PathVariable Integer user_id){
        List<Posts> listfromDB=pagedPostsService.getAllPagedPostsByUserId(pageNo,user_id);

        if (listfromDB == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Cannot fetch paged posts"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(listfromDB, "Successfully retrieved all paged posts"));
    }





}
