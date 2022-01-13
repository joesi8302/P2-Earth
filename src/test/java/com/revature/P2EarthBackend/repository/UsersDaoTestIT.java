package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class UsersDaoTestIT {

    @Autowired
    UsersDao usersDao;

    @Test
    void findAllUsersbyUsername() {

        //arrange
        Users expectedResult = new Users(3,"archer01","123456","Sterling","Archer","","archer01@email.com");
        usersDao.save(expectedResult);


        //act
        Users actualResult=this.usersDao.findAllUsersbyUsername("archer01");

        //assert
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void findAllUsersbyEmail() {
        //arrange
        Users expectedResult = new Users(1,"archer01","123456","Sterling","Archer","","archer01@email.com");
        usersDao.save(expectedResult);


        //act
        Users actualResult=this.usersDao.findAllUsersbyEmail("archer01@email.com");

        //assert
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void findAllUsersBySpecificUsername() {
        //arrange
        Users expectedResult = new Users(2,"archer01","123456","Sterling","Archer","","archer01@email.com");
        usersDao.save(expectedResult);


        //act
        Users actualResult=this.usersDao.findAllUsersBySpecificUsername("lana01","archer01");

        //assert
        assertEquals(expectedResult,actualResult);
    }
}