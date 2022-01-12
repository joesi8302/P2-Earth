package com.revature.P2EarthBackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.P2EarthBackend.models.LoginDTO;
import com.revature.P2EarthBackend.models.ResponseDTO;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.services.EmailService;
import com.revature.P2EarthBackend.services.UploadService;
import com.revature.P2EarthBackend.services.UsersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    public UsersService usersService;

    @Autowired
    public MockMvc mvc;

    @MockBean
    public EmailService emailService;

    @MockBean
    public UploadService uploadService;



    @Test
    void getUsers() throws Exception {
        Users user1=new Users(1,"archer01","123456","Sterling","Archer","","archer01@email.com");
        Users user2 =new Users(2,"lana01",
                "123456","Lana","Kane","","lana01@email.com",null,null);
        List<Users> usersList= new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);

        Mockito.when(usersService.getAllUsers()).thenReturn(usersList);
        List<Users> usersList2=usersService.getAllUsers();
        RequestBuilder request= MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON);

        ResponseDTO responseDTO=new ResponseDTO(usersList2, "Got all users");
        MvcResult result=mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(responseDTO))).andReturn();
        System.out.println(request);

    }

//   @Test
//    void getOneNullUser() throws Exception {
//       Users user1=new Users(1,"archer01","123456","Sterling","Archer","","archer01@email.com");
//       Users user2 =new Users(2,"lana01",
//               "123456","Lana","Kane","","lana01@email.com",null,null);
//       Users nullUser=null;
//       Mockito.when(usersService.getOneUserByUsername("archer")).thenReturn(null);
//       Users user=usersService.getOneUserByUsername("archer");
//       String username="archer";
//
//       RequestBuilder request= MockMvcRequestBuilders
//               .get("/users/{username}","archer")
//               .accept(MediaType.APPLICATION_JSON);
//
//       ResponseDTO responseDTO =new ResponseDTO(null, "Returned User");
//
//       MvcResult result=mvc.perform(request)
//               .andExpect(MockMvcResultMatchers.status().isBadRequest())
//               .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(responseDTO))).andReturn();
//       System.out.println(request);
//
//
//
//
//   }
    @Test
        void getOneExixstingUser() throws Exception {
        Users user1=new Users(1,"archer01","123456","Sterling","Archer","","archer01@email.com");
        Users user2 =new Users(2,"lana01",
                "123456","Lana","Kane","","lana01@email.com",null,null);
        Mockito.when(usersService.getOneUserByUsername("archer1")).thenReturn(user1);
        Users user=usersService.getOneUserByUsername("archer1");
        String username="archer1";

        RequestBuilder request= MockMvcRequestBuilders
                .get("/users/{username}","archer1")
                .accept(MediaType.APPLICATION_JSON);

        ResponseDTO responseDTO =new ResponseDTO(user, "Returned User");

        MvcResult result=mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(responseDTO))).andReturn();
        System.out.println(request);




    }

//    @Test
//    void createUser() throws Exception {
//
//        MockMultipartFile file
//                = new MockMultipartFile(
//                "file",
//                "hello.txt",
//                MediaType.TEXT_PLAIN_VALUE,
//                "Hello, World!".getBytes()
//        );
//
//        String username1="lana01";
//        String password1="123456";
//        Integer userid1=1;
//        String user_first_name1="Lana";
//        String user_last_name1="Kane";
//        MultipartFile user_img1=null;
//        String user_email1="lana01@email.com";
//        Users user2 =new Users(userid1,username1,
//                password1,user_first_name1,user_last_name1,"user_img",user_email1,null,null);
//        Mockito.when(usersService.validateUserByUsername("lana01")).thenReturn(null);
//
////        Users checkUser=this.usersService.validateUserByUsername("lana01");
//
//        Mockito.when(uploadService.uploadMultiFile(user_img1, "lana01" + "ProfileImg")).thenReturn("/");
//
//
//
//        Mockito.when(usersService.createUser(user2)).thenReturn(user2);
//
//
//
//
//        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//        ResponseDTO responseDTO=new ResponseDTO(new LoginDTO(user2.getUserId(), user2.getUsername()), "User created");
//        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/users").file(file).
//                param("username",username1).
//                param("password",password1).
//                param("user_first_name",user_first_name1).param("user_email",user_email1)).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(responseDTO))).andReturn();
//
//        System.out.println(responseDTO);
//
////        RequestBuilder request= MockMvcRequestBuilders
////                .post("/users").
////                param("username",username1).
////                param("password",password1).
////                param("user_first_name",user_first_name1).
////                param("user_last_name",user_last_name1).multipart("/upload")
////                .file(firstFile).param("user_email",user_email1)
////                .accept(MediaType.APPLICATION_JSON);
//
////
////        MvcResult result=mvc.perform(request)
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(responseDTO))).andReturn();
////
//
//
//
//    }




}