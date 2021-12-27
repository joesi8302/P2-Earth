package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersDao extends JpaRepository<Users, Integer> {

    @Query("from Users where name = :username")
    Users findAllUsersbyUsername(@Param("username") String username);

}
