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


    /**
     * Returns a list of posts Objects containing all posts from database.
     * If size of posts is zero, will return null.
     *
     * @return a list of posts Objects or null
     */
    public List<Posts> getAllPosts(){

        List<Posts> postsList = this.postsDao.findAll();

        if (postsList.isEmpty() || postsList == null) return null;

        logger.info("get all posts "+ postsList);

        return postsList;
    }

    /**
     * Return a list of Posts Objects containing all posts from specified user in database.
     * If list has a size of zero, will return null.
     *
     * @param userId    the userId specifying which post is owned to this specific user
     * @return          a list of Posts Objects owned by the specified userId
     */
    public List<Posts> getAllUserPosts(Long userId){

        List<Posts> postsList = this.postsDao.findAllByUserId(userId);

        if (postsList.isEmpty() || postsList == null) return null;

        logger.info("get all user posts" +postsList);

        return postsList;
    }

    /**
     * Returns specific post based on passed id.
     * Will return null if there is no such post with specified id.
     *
     * @param post_id   the id of the post within the database
     * @return          the Posts Object with the specified id
     */
    public Posts getOnePost(Long post_id){
        Posts post = this.postsDao.findAllByPostId(post_id);

        if (post.getPost_id() == null) return null;

        logger.info("get one post with post_id :" + post_id );

        return post;

    }

    /**
     * Returns post created from the Posts Object specified.
     * If post failed to be created, will return null.
     *
     * @param post  a Posts Object passed to save onto database
     * @return      a Posts Object that was saved onto database from specified Posts Object
     */
    public Posts createPost(Posts post){
//        Users user = usersDao.findById(user_id).orElse(null);

//        if (user == null) return null;
//
//        post.setUser(user);

        Posts postFromDB = this.postsDao.save(post);

        if (postFromDB.getPost_id() == null) return null;

        logger.info("create a post "+post);

        return postFromDB;
    }

    /**
     * Returns updated Posts Object with new Image url.
     *
     * @param postId    the id of the post within the database
     * @param url       the new url to be updated within the post
     * @return          the Posts updated with new picture
     */
    public Posts updatePostImg(Long postId, String url){
        Posts post=postsDao.findAllByPostId(postId);
//        Posts post = postsDao.findById(postId.intValue()).orElse(null);

        if(post == null){
            return null;
        }

        post.setPost_img(url);

        return postsDao.save(post);

    }


}

