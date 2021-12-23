package com.revature.P2EarthBackend.repository;

import com.revature.P2EarthBackend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersDao extends JpaRepository<Users, Integer> {
}
