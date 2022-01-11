package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersDao extends JpaRepository<Users, Integer> {

    @Query("from Users where username = :username")
    Users findAllUsersbyUsername(@Param("username") String username);

   /* @Modifying//is this needed?
    @Query("UPDATE Users SET username = :username, password = :password, user_first_name = :user_first_name, user_last_name = :user_last_name, user_img = :user_img, user_email = :user_email")
    Integer updateUsersbyUsername(@Param("username") String username, @Param("password") String password, @Param("user_first_name") String firstname, @Param("user_last_name") String lastname, @Param("user_img") String user_img, @Param("user_email") String email);*/

    @Query("from Users where user_email = :email")
    Users findAllUsersbyEmail(@Param("email") String email);


    @Query("from Users where username != :original AND username = :username")
    Users findAllUsersBySpecificUsername(@Param("original") String original, @Param("username") String username);

    @Query("from Users where user_email != :original AND user_email = :email")
    Users findAllUsersBySpecificEmail(@Param("original") String original, @Param("email") String username);
}
