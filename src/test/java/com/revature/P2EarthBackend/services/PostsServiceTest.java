package com.revature.P2EarthBackend.services;

import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.repository.PostsDao;
import com.revature.P2EarthBackend.repository.UsersDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostsServiceTest {
    UsersService usersService;
    PostsService postsService;
    PostsDao postsDao=Mockito.mock(PostsDao.class);

    UsersDao usersDao = Mockito.mock(UsersDao.class);

    public PostsServiceTest(){
        this.postsService=new PostsService(postsDao);
    }

    @Test
    void getAllPosts() {
        //arrange


        Users user1 =new Users(1,"archer01",
                "123456","Archer","Sterling","","archer01@email.com",null,null);
        Users user2 =new Users(2,"lana01",
                "123456","Lana","Kane","","lana01@email.com",null,null);
        Users user3 =new Users(3,"Cyril01",
                "123456","Cyril","Figgis","","cyril01@email.com",null,null);

        Posts post1=new Posts(1L,new Timestamp(System.currentTimeMillis()),"This is post id 1","this is post_img 1",user1,null);
        Posts post2=new Posts(2L,new Timestamp(System.currentTimeMillis()),"This is post id 2","this is post_img 2",user2,null);
        Posts post3=new Posts(3L,new Timestamp(System.currentTimeMillis()),"This is post id 3","this is post_img 3",user3,null);
        List<Posts> expectedResult=new ArrayList<>();
        expectedResult.add(post1);
        expectedResult.add(post2);
        expectedResult.add(post3);

        Mockito.when(this.postsDao.findAll()).thenReturn(expectedResult);


        //act
        List<Posts> actualResult =this.postsService.getAllPosts();

        //assert
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void getAllUserPosts() {

        //arrange


        Users user1 =new Users(1,"archer01",
                "123456","Archer","Sterling","","archer01@email.com",null,null);
        Users user2 =new Users(2,"lana01",
                "123456","Lana","Kane","","lana01@email.com",null,null);
        Users user3 =new Users(3,"Cyril01",
                "123456","Cyril","Figgis","","cyril01@email.com",null,null);

        Posts post1=new Posts(1L,new Timestamp(System.currentTimeMillis()),"This is post id 1","this is post_img 1",user1,null);
        Posts post2=new Posts(2L,new Timestamp(System.currentTimeMillis()),"This is post id 2","this is post_img 2",user2,null);
        Posts post3=new Posts(3L,new Timestamp(System.currentTimeMillis()),"This is post id 3","this is post_img 3",user3,null);
        Posts post4=new Posts(4L,new Timestamp(System.currentTimeMillis()),"This is post id 4","this is post_img 4",user1,null);
        Posts post5=new Posts(5L,new Timestamp(System.currentTimeMillis()),"This is post id 5","this is post_img 5",user1,null);

        List<Posts> expectedResult=new ArrayList<>();
        expectedResult.add(post1);
        expectedResult.add(post4);
        expectedResult.add(post5);

        Mockito.when(this.postsDao.findAllByUserId(Long.valueOf(user1.getUserId()))).thenReturn(expectedResult);

        //act
        List<Posts> actualResult =this.postsService.getAllUserPosts(Long.valueOf(user1.getUserId()));

        //assert
        assertEquals(actualResult,expectedResult);

    }

    @Test
    void createPost() {
        //arrange


        Users user1 =new Users(1,"archer01",
                "123456","Archer","Sterling","","archer01@email.com",null,null);
        Users user2 =new Users(2,"lana01",
                "123456","Lana","Kane","","lana01@email.com",null,null);
        Users user3 =new Users(3,"Cyril01",
                "123456","Cyril","Figgis","","cyril01@email.com",null,null);

        Posts post1=new Posts(1L,new Timestamp(System.currentTimeMillis()),"This is post id 1","this is post_img 1",user1,null);
        Posts post2=new Posts(2L,new Timestamp(System.currentTimeMillis()),"This is post id 2","this is post_img 2",user2,null);
        Posts post3=new Posts(3L,new Timestamp(System.currentTimeMillis()),"This is post id 3","this is post_img 3",user3,null);
        Posts post4=new Posts(4L,new Timestamp(System.currentTimeMillis()),"This is post id 4","this is post_img 4",user1,null);
        Posts post5=new Posts(5L,new Timestamp(System.currentTimeMillis()),"This is post id 5","this is post_img 5",user1,null);

        List<Posts> postsList=new ArrayList<>();
        postsList.add(post1);
        postsList.add(post4);
        postsList.add(post5);

        Mockito.when(this.postsDao.save(post1)).thenReturn(post1);

        //act
        Posts actualResult =this.postsService.createPost(post1);
        Posts expectedResult=this.postsDao.save(post1);

        //assert
        assertEquals(actualResult,expectedResult);

        // TODO: 1/5/2022  1.controller test 2.dao test 


    }
}