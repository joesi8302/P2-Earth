package com.revature.P2EarthBackend.controllers;


import com.revature.P2EarthBackend.models.LoginDTO;
import com.revature.P2EarthBackend.models.ResponseDTO;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.services.EmailService;
import com.revature.P2EarthBackend.services.UploadService;
import com.revature.P2EarthBackend.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "users")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UsersController {

    private UsersService usersService;
    private EmailService emailService;
    private UploadService uploadService;

    @Autowired
    public UsersController(UsersService usersService, EmailService emailService, UploadService uploadService){
        this.usersService = usersService;
        this.emailService = emailService;
        this.uploadService = uploadService;
    }

    public UsersController(UsersService usersService) {
        this.usersService=usersService;
    }


    @GetMapping
    public ResponseEntity<ResponseDTO> getUsers(){
        List<Users> usersList = usersService.getAllUsers();

        //list was not found or dose not exist
        if (usersList == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        //returns list with ok status code
        return ResponseEntity.ok(new ResponseDTO(usersList, "Got all users"));

    }

    @GetMapping("{username}")
    public ResponseEntity<ResponseDTO> getOneUser(@PathVariable String username){
        Users user = usersService.getOneUserByUsername(username);

        //user was not found or dose not exist
        if (user == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        //returns user with ok status code
        return ResponseEntity.ok(new ResponseDTO(user, "Returned User"));

    }


    // I droped the user_id parameter here , I think when we create the user we dont need to provide the id , it should autoincreate and autogenerate ,
// other wise we have to memeorize the user id number, if we use the same id number ,it will replace the user with the same user id
    @PostMapping
    public ResponseEntity<ResponseDTO> createUser(
                                                  @RequestParam String username,
                                                  @RequestParam String password,
                                                  @RequestParam String user_first_name,
                                                  @RequestParam String user_last_name,
                                                  @RequestParam MultipartFile user_img,
                                                  @RequestParam String user_email ) throws IOException {

        Users userInput = new Users( username, password, user_first_name, user_last_name, null, user_email);

        Users checkUser = this.usersService.validateUserByUsername(username);

        //username was already taken
        if(checkUser != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "This username already exists please try a different one"));
        }

        //returns ok status code with user id and username passed back
        String url = uploadService.uploadMultiFile(user_img, username + "ProfileImg");
        userInput.setUser_img(url);
        Users user = this.usersService.createUser(userInput);
        return ResponseEntity.ok(new ResponseDTO(new LoginDTO(user.getUser_id(), user.getUsername()), "User created"));

    }
// I droped the user_id parameter here , I think when we create the user we dont need to provide the id , it should autoincreate and autogenerate ,
// other wise we have to memeorize the user id number, if we use the same id number ,it will replace the user with the same user id
    @PutMapping
    public ResponseEntity<ResponseDTO> updateUser(
                                     @RequestParam Integer id,
                                     @RequestParam String username,
                                     @RequestParam String password,
                                     @RequestParam String user_first_name,
                                     @RequestParam String user_last_name,
                                     @RequestParam MultipartFile user_img,
                                     @RequestParam String user_email) throws IOException {

        ResponseEntity<ResponseDTO> responseEntity;
        Users user = new Users( id, username, password, user_first_name, user_last_name, null, user_email);

        Users userfromDB = this.usersService.updateUser(user, user_img);

        if (userfromDB == null) {

            return ResponseEntity.
                    status(HttpStatus.NO_CONTENT)
                    .body(new ResponseDTO(null, "Cannot find user in database"));
        }

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(userfromDB, "Returned user from database"));

    }

    //forgot password
    @PutMapping("reset")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestParam String email){
        ResponseEntity<ResponseDTO> responseEntity;
        Integer length = email.length();
        String newEmail = email.substring(1,length-1);
        System.out.println(newEmail);
        Users userfromDB = this.usersService.resetPassword(newEmail);

        if (userfromDB == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new ResponseDTO(null, "Cannot find user in database"));
        }else {

            this.emailService.sendEmail(userfromDB.getUser_email());//check if this works
            responseEntity = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(userfromDB, "Email has been sent"));
        }

        return responseEntity;
    }
}
