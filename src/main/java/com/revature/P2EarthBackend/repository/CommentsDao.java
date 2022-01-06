package com.revature.P2EarthBackend.repository;



import com.revature.P2EarthBackend.models.Comments;
import com.revature.P2EarthBackend.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsDao extends JpaRepository<Comments,Long>{
    @Query("from Comments where user_user_id = :user_user_id")
    List<Comments> findAllByUserId(@Param("user_user_id") Long user_id);


    @Query("from Comments where comment_id= :comment_id")
    Comments findAllByCommentId(@Param("comment_id")  Long comment_id);

    @Query("from Posts where post_id= :post_id")
    List<Comments> findAllByPostId(@Param("post_id") Long post_id);


}
