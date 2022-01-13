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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "pagedposts")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
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

    /**
     * Returns a ResponseEntity Object to send to FrontEnd.
     * Will give a page Number to be sent to the service layer for a list to be sent back.
     * If the list is null, then will return with message "Cannot fetch paged posts".
     * If the list is not null, then will return list with message "Successfully retrieved all paged posts"
     *
     * @param pageNo    the page number of the posts you would like to see
     * @return          the ResponseEntity with set message and/or list of Posts Objects
     */
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

    /**
     * Returns a ResponseEntity Object with a list of Posts Objects and message to send to FrontEnd.
     * Will give a page Number and userID to be sent to the service layer for a list to be sent back.
     * If the list is null, then will return with message "Cannot fetch paged posts".
     * If the list is not null, then will return list with message "Successfully retrieved all paged posts".
     *
     * @param pageNo    the page number of the posts you would like to see
     * @param user_id   the user's id to determine which posts you would like to receive
     * @return          the ResponseEntity with set message and/or list of Posts Objects by specified parameters
     */
    @GetMapping("/{pageNo}/{user_id}")
    public ResponseEntity<ResponseDTO> getAllPagedPostsByUserId(@PathVariable Integer pageNo,@PathVariable Integer user_id){

        List<Posts> listfromDB = pagedPostsService.getAllPagedPostsByUserId(pageNo, user_id);

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
