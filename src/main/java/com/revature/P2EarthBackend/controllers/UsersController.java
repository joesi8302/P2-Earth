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


    /**
     * Returns ResponseEntity with message: "Got all users" and list of Users Objects.
     *
     * @return the ResponseEntity Object with message: "Got all users" and list of Users Objects
     */
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

    /**
     * Returns ResponseEntity with message: "Returned User" and list of Users Objects or null.
     *
     * @param username  the username of the user you would like to receive
     * @return          the ResponseEntity with message: "Returned User" and list of Users Objects or null
     */
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

    /**
     * Returns ResponseEntity Object of the created user and message: "User created".
     * If username is already taken, then message will be: "This username already exists please try a different one".
     * If email is already taken, then message will be: "This email is already registered".
     *
     * @param username          the username for the new user
     * @param password          the password for the new user
     * @param user_first_name   the first name of the new user
     * @param user_last_name    the last name of the new user
     * @param user_img          the profile image to show on the user account page
     * @param user_email        the email of the new user
     * @return                  the ResponseEntity Object of the created user and message: "User created"
     * @throws IOException      for the upload image function
     */
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

        if (!usersService.isEmailUnique(user_email)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "This email is already registered"));
        }

        //returns ok status code with user id and username passed back
        String url = uploadService.uploadMultiFile(user_img, username + "ProfileImg");
        userInput.setUser_img(url);
        Users user = this.usersService.createUser(userInput);
        return ResponseEntity.ok(new ResponseDTO(new LoginDTO(user.getUserId(), user.getUsername()), "User created"));

    }
// I droped the user_id parameter here , I think when we create the user we dont need to provide the id , it should autoincreate and autogenerate ,
// other wise we have to memeorize the user id number, if we use the same id number ,it will replace the user with the same user id

    /**
     * Returns ResponseEntity Object of the updated user and message: "Updated user".
     * If username is already taken, then message will be: "Username is already taken".
     * If email is already taken, then message will be: "This email is already registered".
     *
     * @param id                the id of the user you would like to update
     * @param username          the new username of the user
     * @param password          the new password of the user
     * @param user_first_name   the new first name of the user
     * @param user_last_name    the new last name of the user
     * @param user_img          the new profile image of the user
     * @param user_email        the new email of the user
     * @return                  the ResponseEntity Object of the updated user and message: "Updated user"
     * @throws IOException      for the upload image function
     */

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
                    status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Cannot find user in database"));
        }

        if (userfromDB.getUser_img().equals("-1")) {
            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Username is already taken"));
        }

        if (userfromDB.getUser_img().equals("-2")) {
            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Email is already registered"));
        }

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(userfromDB, "Updated User"));

    }

    //forgot password

    /**
     * Returns ResponseEntity Object with the user of the specified email and message: "Email has been sent".
     * If the email was not found then will send message: "Cannot find user in database"
     *
     * @param email the email of the user you want to reset the password to
     * @return      the ResponseEntity Object with the user of the specified email and message: "Email has been sent".
     */
    @PutMapping("reset")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestParam String email){
        ResponseEntity<ResponseDTO> responseEntity;
        Integer length = email.length();
        String newEmail = email.substring(1,length-1);
        System.out.println(newEmail);
        Users userfromDB = this.usersService.resetPassword(newEmail);

        if (userfromDB == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
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
