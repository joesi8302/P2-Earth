package com.revature.P2EarthBackend.controllers;


import com.revature.P2EarthBackend.models.LoginDTO;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.services.UsersService;
import jdk.jpackage.internal.Log;
import lombok.var;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "users")
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {

    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping
    public LoginDTO loginDTO(@RequestBody Users requestBody){
        Users user = this.usersService.createUser(requestBody);

        if(user == null) {
            return new LoginDTO(null, null, "This username already exists please try a different one");
        }

        return new LoginDTO(user.getUser_id(), user.getUsername(), "User created");
    }

    @PostMapping
    public Users createUser(@RequestBody Users user){
        return this.usersService.createUser(user);
    }

    @PutMapping
    public ResponseEntity<Users> updateUser(@RequestParam Integer id,
                                     @RequestParam String username,
                                     @RequestParam String password,
                                     @RequestParam String firstname,
                                     @RequestParam String lastname,
                                     @RequestParam String userimg,
                                     @RequestParam String email){

        ResponseEntity<Users> responseEntity;
        Users user = new Users(id, username, password, firstname, lastname, userimg, email, null);

        Users userfromDB = this.usersService.updateUser(user);

        if (userfromDB == null) {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(userfromDB);
        }

        return responseEntity;
    }

    //forgot password

}
