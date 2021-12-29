package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersDao extends JpaRepository<Users, Integer> {

    @Query("from Users where name = :username")
    Users findAllUsersbyUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE Users SET username = :username, password = :password, user_first_name = :firstname, user_last_name = :lastname, user_img = :user_img, user_email = :email")
    Users updateUsersbyUsername(@Param("username") String username, @Param("password") String password, @Param("user_first_name") String firstname, @Param("user_last_name") String lastname, @Param("user_img") String user_img, @Param("user_email") String email);

}
