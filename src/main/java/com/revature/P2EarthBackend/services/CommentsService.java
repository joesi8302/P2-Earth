package com.revature.P2EarthBackend.services;

import com.revature.P2EarthBackend.models.Comments;
import com.revature.P2EarthBackend.models.Posts;
import com.revature.P2EarthBackend.repository.CommentsDao;
import com.revature.P2EarthBackend.repository.PostsDao;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
@Data

public class CommentsService {

    private CommentsDao commentsDao;


    @Autowired
    public CommentsService(CommentsDao commentsDao){

        this.commentsDao = commentsDao;
    }

    public List<Comments> getAllComments(){

        List<Comments> commentsList = this.commentsDao.findAll();

        if (commentsList == null) return null;

        return commentsList;
    }

    public List<Comments> getAllUserComments(Long user_Id){

        return this.commentsDao.findAllByUserId(user_Id);

    }

    public List<Comments> getAllPostComments(Long post_Id){

        List<Comments> commentsList = this.commentsDao.findAllByPostId(post_Id);

        if (commentsList.isEmpty() || commentsList == null) return null;

        return commentsList;
    }

    public Comments getOneComment(Long comment_id){

        Comments comment = this.commentsDao.findAllByCommentId(comment_id);

        if (comment == null) return null;

        return comment;
    }

    public Comments createComment(Comments comment){

        if (comment.getComment_description() == null) return null;

        return this.commentsDao.save(comment);
    }

    public void deleteComment(Comments comment){

        this.commentsDao.delete(comment);
    }
}






