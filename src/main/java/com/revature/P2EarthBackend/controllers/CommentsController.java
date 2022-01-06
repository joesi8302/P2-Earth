package com.revature.P2EarthBackend.controllers;

import com.revature.P2EarthBackend.models.*;
import com.revature.P2EarthBackend.repository.CommentsDao;
import com.revature.P2EarthBackend.repository.PostsDao;
import com.revature.P2EarthBackend.repository.UsersDao;
import com.revature.P2EarthBackend.services.CommentsService;
import com.revature.P2EarthBackend.services.PostsService;
import com.revature.P2EarthBackend.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping(value = "comments")
public class CommentsController {
    @Autowired
    private PostsDao postsDao;


    @Autowired
    private PostsService postsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private CommentsDao commentsDao;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    public CommentsController(PostsService postsService,UsersService usersService,CommentsService commentsService){

        this.postsService = postsService;
        this.usersService = usersService;
        this.commentsService = commentsService;


    }

    // TODO: 1/2/2022 need a check if the post we want to add comment on  exists, if not returns a message
    //create a comment
    @PostMapping
    public ResponseEntity<ResponseDTO> createComment(@RequestBody CommentBody requestBody, HttpSession httpSession){

        String comment_description = requestBody.getComment_description();

        Integer user_Id = (Integer) httpSession.getAttribute("user-session");
        Long post_id = requestBody.getPost_id();
        Users user = usersService.getOneUser(user_Id);

        ResponseEntity<ResponseDTO> responseEntity;

        if (user == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(null, "Could not find the user"));

            return responseEntity;
        }

        Posts post = postsService.getOnePost(post_id);

        if (post == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(null, "Could not find the post"));
        } else {
            Comments comment = new Comments(comment_description,user,post);



            Comments commentFromDB = commentsService.createComment(comment);

            if (commentFromDB == null) {
                responseEntity = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(null, "Description not found"));

                return responseEntity;
            }

            responseEntity = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(commentFromDB, "Comment successfully created"));
        }

        return responseEntity;

    }
//get all the comments
    @GetMapping
    public ResponseEntity<ResponseDTO> getAllComments(){

        ResponseEntity<ResponseDTO> responseEntity;

        List<Comments> comments = commentsService.getAllComments();

        if (comments == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Comments not found"));

            return responseEntity;
        }

        return responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(comments, "Successfully retrieved all comments from Database"));
    }

    //get a comment by commentId
    @GetMapping("{commentId}")
    public ResponseEntity<ResponseDTO> getOneComment(@PathVariable Long commentId){

        Comments comment = commentsService.getOneComment(commentId);

        ResponseEntity<ResponseDTO> responseEntity;

        if (comment == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Comment not found"));

            return responseEntity;
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(comment, "Successfully retrieved comment from Database"));
    }

   /* //get all the comments by the logged in user(in session)
    @GetMapping("user")
    ResponseEntity<ResponseDTO> getAllMyComments(HttpSession httpSession){

        Integer user_Id = (Integer) httpSession.getAttribute("user-session");

        List<Comments> comments=commentsService.getAllUserComments(Long.valueOf(user_Id));


        return comments;
    }*/



/*//get all the comments by user_id
    @GetMapping("user/{user_id}")
    List<Comments> getAllUserComments(@PathVariable Integer user_id){

        List<Comments> comments=commentsService.getAllUserComments(Long.valueOf(user_id));


        return comments;
    }*/

    //get comments by post_id
    @GetMapping("post/{post_id}")
    public ResponseEntity<ResponseDTO> getAllPostComments(@PathVariable Long post_id){

        List<Comments> comments=commentsService.getAllPostComments(post_id);

        ResponseEntity<ResponseDTO> responseEntity;

        if (comments == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Comment not found"));

            return responseEntity;
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(comments, "Successfully retrieved all comments for post Id: " + post_id));
    }

    @DeleteMapping("delete/{comment_id}")
    public ResponseEntity<ResponseDTO> deleteComment(@PathVariable Long comment_id){

        Comments comment = this.commentsService.getOneComment(comment_id);

        ResponseEntity<ResponseDTO> responseEntity;

        if (comment == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Comment not found"));

            return responseEntity;
        }

        this.commentsService.deleteComment(comment);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(comment, "Successfully deleted comment for with Id: " + comment_id));

    }


}
