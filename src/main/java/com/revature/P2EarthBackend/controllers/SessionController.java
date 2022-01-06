package com.revature.P2EarthBackend.controllers;


import com.revature.P2EarthBackend.models.LoginDTO;
import com.revature.P2EarthBackend.models.LoginInfo;
import com.revature.P2EarthBackend.models.ResponseDTO;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.services.UsersService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "session")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@Data
public class SessionController {


    private UsersService usersService;

    @Autowired
    public SessionController(UsersService usersService){
        this.usersService = usersService;
    }

    //create session
    @PostMapping
    public ResponseEntity<ResponseDTO> createSession(HttpSession httpSession, @RequestBody LoginInfo loginInfo){
        Users user = this.usersService.loginUser(loginInfo);

        if(user == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Invalid username or password"));

        httpSession.setAttribute("user-session", user.getUser_id());
        return ResponseEntity.ok(new ResponseDTO(new LoginDTO(user.getUser_id(), user.getUsername()), "login successful"));
    }

    //delete session
    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteSession(HttpSession httpSession){
        httpSession.invalidate();

        return ResponseEntity.ok(new ResponseDTO(null, "session logged out"));
    }

    //check session
    @GetMapping
    public ResponseEntity<ResponseDTO> checkUserSession(HttpSession httpSession){
        Integer userId = (Integer) httpSession.getAttribute("user-session");

        if(userId == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "no session found"));

        Users users = this.usersService.getOneUser(userId);

        if(users == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "no session found"));
        System.out.println();

        return ResponseEntity.ok(new ResponseDTO(new LoginDTO(users.getUser_id(), users.getUsername()), "session found"));

    }
}
