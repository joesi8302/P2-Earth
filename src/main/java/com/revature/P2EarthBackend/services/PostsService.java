package com.revature.P2EarthBackend.services;

import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.repository.PostsDao;
import com.revature.P2EarthBackend.repository.UsersDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class PostsService {
    private PostsDao postsDao;
    private UsersDao usersDao;
    Logger logger =Logger.getLogger(PostsService.class);

    @Autowired
    public PostsService(PostsDao postsDao){
        this.postsDao = postsDao;
    }

    public List<Posts> getAllPosts(){

        List<Posts> postsList = this.postsDao.findAll();

        if (postsList.isEmpty() || postsList == null) return null;

        logger.info("get all posts "+ postsList);

        return postsList;
    }

    public List<Posts> getAllUserPosts(Long userId){

        List<Posts> postsList = this.postsDao.findAllByUserId(userId);

        if (postsList.isEmpty() || postsList == null) return null;

        logger.info("get all user posts" +postsList);

        return postsList;
    }

    public Posts getOnePost(Long post_id){
        Posts post = this.postsDao.findAllByPostId(post_id);

        if (post.getPost_id() == null) return null;

        logger.info("get one post with post_id :"+post_id );

        return post;

    }

    public Posts createPost(Posts post){
        //todo fix create post
//        Users user = usersDao.findById(user_id).orElse(null);

//        if (user == null) return null;
//
//        post.setUser(user);

        Posts postFromDB = this.postsDao.save(post);

        if (postFromDB.getPost_id() == null) return null;

        logger.info("create a post "+post);

        return postFromDB;
    }

    public Posts updatePostImg(Long postId, String url){
        Posts post=postsDao.findAllByPostId(postId);
//        Posts post = postsDao.findById(postId.intValue()).orElse(null);

        if(post == null){
            return null;
        }

        post.setPost_img(url);

        return postsDao.save(post);

    }

    public void deletePost(Posts post){

         this.postsDao.delete(post);
    }


}

