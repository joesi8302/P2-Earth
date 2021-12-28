package com.revature.P2EarthBackend.services;

import com.revature.P2EarthBackend.models.LoginDTO;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.repository.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.jasypt.util.password.BasicPasswordEncryptor;

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

        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());

        user.setPassword(encryptedPassword);

        return this.usersDao.save(user);
    }

    public Boolean deleteUser(Integer user_id){
        return null;
    }
    public Users loginUser(LoginDTO loginDTO) {

        Users user=usersDao.findAllUsersbyUsername(loginDTO.getUsername());
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        boolean passCheck = passwordEncryptor.checkPassword(loginDTO.getPassword(), user.getPassword());


        if( user==null){
            return null;
        }else{
            if(passCheck){
                return user;
            }else{
                return null;
            }
        }
    }

}
