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

    /**
     * Returns ResponseEntity Object with the session created and the login info used.
     * If was given invalid login info, will return message: "Invalid username or password".
     * If was given valid login info, will return message: "login successful".
     *
     * @param httpSession the session variable created
     * @param loginInfo   the login info used to sign in to the user account
     * @return            the ResponseEntity Object with the session created and the login info used.
     */
    //create session
    @PostMapping
    public ResponseEntity<ResponseDTO> createSession(HttpSession httpSession, @RequestBody LoginInfo loginInfo){
        Users user = this.usersService.loginUser(loginInfo);

        if(user == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(null, "Invalid username or password"));

        httpSession.setAttribute("user-session", user.getUserId());
        return ResponseEntity.ok(new ResponseDTO(new LoginDTO(user.getUserId(), user.getUsername()), "login successful"));
    }

    /**
     * Returns ResponseEntity Object with a message saying the session was logged out
     * @param httpSession  the session variable created
     * @return             the ResponseEntity Object with a message saying the session was logged out
     */
    //delete session
    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteSession(HttpSession httpSession){
        httpSession.invalidate();

        return ResponseEntity.ok(new ResponseDTO(null, "session logged out"));
    }

    /**
     * Returns ResponseEntity Object with a message: "no session found" or "session found" with user in session
     *
     * @param httpSession   the session variable created
     * @return              the ResponseEntity Object with a message: "no session found" or "session found" with user in session
     */
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

        return ResponseEntity.ok(new ResponseDTO(new LoginDTO(users.getUserId(), users.getUsername()), "session found"));

    }
}
