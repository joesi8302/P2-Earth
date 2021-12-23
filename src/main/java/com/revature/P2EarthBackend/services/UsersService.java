package com.revature.P2EarthBackend.services;

import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.repository.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UsersService {

    private UsersDao usersDao;

    @Autowired
    public UsersService(UsersDao usersDao){
        this.usersDao = usersDao;
    }

    public List<Users> getAllUsers(){
        return this.usersDao.findAll();
    }

    public Users getOneUser(Integer user_id){
        return this.usersDao.getById(user_id);
    }

    public Users createUser(Users user){
        return this.usersDao.save(user);
    }

    public Boolean deleteUser(Integer user_id){
        return null;
    }
}
