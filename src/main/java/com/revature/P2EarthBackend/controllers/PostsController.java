package com.revature.P2EarthBackend.controllers;


import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.models.ResponseDTO;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.services.PostsService;
import com.revature.P2EarthBackend.services.UploadService;
import com.revature.P2EarthBackend.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "posts")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PostsController {

    private PostsService postsService;
    private UsersService usersService;
    private UploadService uploadService;

    @Autowired
    public PostsController(PostsService postsService, UsersService usersService, UploadService uploadService){
        this.postsService = postsService;
        this.usersService = usersService;
        this.uploadService = uploadService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllPosts(){

        List<Posts> posts = postsService.getAllPosts();

        ResponseEntity<ResponseDTO> responseEntity;

        if (posts == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Failed to fetch all posts"));

            return responseEntity;
        }

        responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(posts, "Successfully retrieved all posts"));
        return responseEntity;
    }

    @GetMapping("{postId}")
    public ResponseEntity<ResponseDTO> getOnePost(@PathVariable Long postId){

        Posts post = postsService.getOnePost(postId);

        ResponseEntity<ResponseDTO> responseEntity;

        if (post == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Failed to fetch post with id: " + postId));

            return responseEntity;
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(post, "Successfully got post with id: " + postId));
    }


    //get all the posts of the logged in user
    @GetMapping("user")
    ResponseEntity<ResponseDTO> getAllMyPosts(HttpSession httpSession){

        Integer user_id = (Integer) httpSession.getAttribute("user-session");

        List<Posts> posts = postsService.getAllUserPosts(Long.valueOf(user_id));

        ResponseEntity<ResponseDTO> responseEntity;

        if (posts == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Failed to fetch all post from user id: " + user_id));

            return responseEntity;
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(posts, "Successfully got all posts from user id: " + user_id));
    }


    //get all the posts by user_id
    // TODO: 1/2/2022 need to add a check , if returns null
    @GetMapping("user/{user_id}")
    ResponseEntity<ResponseDTO> getAllUserPosts(@PathVariable String username){

        Users user = usersService.getOneUserByUsername(username);
        Integer user_id = user.getUserId();

        List<Posts> posts = postsService.getAllUserPosts(Long.valueOf(user_id));

        ResponseEntity<ResponseDTO> responseEntity;

        if (posts == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Failed to fetch all post from user id: " + user_id));

            return responseEntity;
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(posts, "Successfully got all posts from user id: " + user_id));
    }


    /**
     * Returns ResponseEntity Object with post created and status message.
     * If post failed to be created will send "Failed to create post".
     * If post was created and saved, will send "Successfully created post".
     *
     * @param postImg       the image that will be displayed on the user's posts
     * @param description   the description of the post
     * @param httpSession   the session to determine which user is creating the post
     * @return              the ResponseEntity Object with post created and status message.
     * @throws IOException  exception for the upload Image
     */
//deleted post_id parameter , it should autogenerate and autoencrement
    @PostMapping
    public ResponseEntity<ResponseDTO> createPost(@RequestParam MultipartFile postImg, @RequestParam String description, HttpSession httpSession) throws IOException {

        String post_description = description;
        String post_img = null;
        Integer user_Id = (Integer) httpSession.getAttribute("user-session");
        Users user=usersService.getOneUser(user_Id);

        ResponseEntity<ResponseDTO> responseEntity;

        Posts objPost = postsService.createPost(new Posts(post_description,null,user));

        if (objPost == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Failed to create post"));

            return responseEntity;
        }

        String url = uploadService.uploadMultiFile(postImg, objPost.getUser().getUsername() + objPost.getPost_id());

        Posts updatedPost = postsService.updatePostImg(objPost.getPost_id(), url);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(objPost.toString(), "Successfully created post"));
    }

}
