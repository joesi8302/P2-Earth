package com.revature.P2EarthBackend.services;

import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.repository.UsersDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceTest {

    UsersService usersService;

    UsersDao usersDao = Mockito.mock(UsersDao.class);

    public UsersServiceTest(){
        this.usersService=new UsersService(usersDao);
    }


    @Test
    void getAllUsers() {
        //arrange
        Users user1 =new Users(1,"archer01",
                "123456","Archer","Sterling","","archer01@email.com",null,null);
        Users user2 =new Users(2,"lana01",
                "123456","Lana","Kane","","lana01@email.com",null,null);
        Users user3 =new Users(3,"Cyril01",
                "123456","Cyril","Figgis","","cyril01@email.com",null,null);
        List<Users> expectedResult=new ArrayList<>();
        expectedResult.add(user1);
        expectedResult.add(user2);
        expectedResult.add(user3);

        Mockito.when(this.usersDao.findAll()).thenReturn(expectedResult);


        //act
        List<Users> actualResult =this.usersService.getAllUsers();

        //assert
        assertEquals(expectedResult,actualResult);

    }

    @Test
    void getOneUser() {
        //arrange
        Users user1 =new Users(1,"archer01",
                "123456","Archer","Sterling","","archer01@email.com",null,null);
        Users user2 =new Users(2,"lana01",
                "123456","Lana","Kane","","lana01@email.com",null,null);
        Users user3 =new Users(3,"Cyril01",
                "123456","Cyril","Figgis","","cyril01@email.com",null,null);
        List<Users> usersList=new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);

        Mockito.when(this.usersDao.getById(user1.getUserId())).thenReturn(user1);

        //act
        Users actualResult=this.usersService.getOneUser(1);
        Users expectedResult=this.usersDao.getById(1);


        //assert
        assertEquals(expectedResult,actualResult);

    }

    @Test
    void createUser() {
        //arrange
        Users user1 =new Users(1,"archer01",
                "123456","Archer","Sterling","","archer01@email.com",null,null);
        Users user2 =new Users(2,"lana01",
                "123456","Lana","Kane","","lana01@email.com",null,null);
        Users user3 =new Users(3,"Cyril01",
                "123456","Cyril","Figgis","","cyril01@email.com",null,null);

        Mockito.when(this.usersDao.save(user1)).thenReturn(user1);

        //act
        Users actualResult =this.usersService.createUser(user1);
        Users expectedResult=this.usersDao.save(user1);

        //assert
        assertEquals(expectedResult,actualResult);


    }

    @Test
    void validateUserByUsername() {

        //arrange
        Users user1 =new Users(1,"archer01",
                "123456","Archer","Sterling","","archer01@email.com",null,null);




    }



    @Test
    void deleteUser() {
    }

    @Test
    void loginUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void resetPassword() {
    }
}