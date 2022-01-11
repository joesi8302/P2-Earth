package com.revature.P2EarthBackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.P2EarthBackend.models.LoginDTO;
import com.revature.P2EarthBackend.models.LoginInfo;
import com.revature.P2EarthBackend.models.ResponseDTO;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.repository.UsersDao;
import com.revature.P2EarthBackend.services.UsersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
    public UsersService usersService;

    @MockBean
    public UsersDao usersDao;

    @MockBean
    public  MockHttpSession session;

    @Test
    void createSession() throws Exception{
        Users user1=new Users(1,"archer01","123456","Sterling","Archer","","archer01@email.com");
        LoginInfo loginInfo=new LoginInfo();
        loginInfo.setUsername("archer01");
        loginInfo.setPassword("123456");
        System.out.println("this is logininfo " +loginInfo);
        Users user = this.usersService.loginUser(loginInfo);


        System.out.println("this is user "+user1);
        ResponseDTO expectedResult =new ResponseDTO(new LoginDTO(user1.getUser_id(), user1.getUsername()), "login successful");
        System.out.println("this is expected result"+ expectedResult);

                Mockito.when(this.usersService.loginUser(loginInfo))
                .thenReturn(user1);

        mvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginInfo))
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));

        Mockito.verify(this.session,Mockito.times(1)).setAttribute("user-session", user1.getUser_id());


//
//        System.out.println(user);
//
//        System.out.println(expectedResult);

//
//
//
//        System.out.println(session);
//
//        assertEquals(user,user1);
    }
}
