package com.revature.P2EarthBackend.controllers;

import com.revature.P2EarthBackend.models.ResponseDTO;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.services.UsersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;


class UsersControllerTest {
    UsersService usersService = Mockito.mock(UsersService.class);

    UsersController usersController;

    public UsersControllerTest(){
        this.usersController=new UsersController(usersService);
    }

    @Test
    void getUsers() {
    }

    @Test
    void createUserWithValidUsername() {


        //arrange
        Users user1 =new Users(1,"archer01",
                "123456","Archer","Sterling","","archer01@email.com",null,null);
        Users user2 =new Users(2,"lana01",
                "123456","Lana","Kane","","lana01@email.com",null,null);
        Users user3 =new Users(3,"Cyril01",
                "123456","Cyril","Figgis","","cyril01@email.com",null,null);
        ResponseEntity<ResponseDTO> expecyedResult =ResponseEntity.ok(new ResponseDTO());



    }

    @Test
    void updateUser() {
    }

    @Test
    void resetPassword() {
    }
}