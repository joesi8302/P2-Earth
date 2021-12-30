package com.revature.P2EarthBackend.services;

import com.revature.P2EarthBackend.models.LoginDTO;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.repository.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
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

        Users user1 = usersDao.findAllUsersbyUsername(user.getUsername());

        if (user1 != null) { // if not null that means it finds something

            return null;
        }else { //if null that means it didnt find a username and we can add a username

            BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
            String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());

            user.setPassword(encryptedPassword);

            return this.usersDao.save(user);

        }
    }

    public Boolean deleteUser(Integer user_id){
        return null;
    }
    public Users loginUser(Users users) {

        Users checkuser = usersDao.findAllUsersbyUsername(users.getUsername());
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        boolean passCheck = passwordEncryptor.checkPassword(users.getPassword(), checkuser.getPassword());


        if(checkuser == null){
            return null;
        }else{
            if(passCheck){
                return checkuser;
            }else{
                return null;
            }
        }
    }

    public Users updateUser(Users user) {

        Users checkuser = usersDao.findAllUsersbyUsername(user.getUsername());

        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        if(checkuser == null){
            return null;
        }else{
            //set checkuser = user --- idk if it works

            Integer changed = usersDao.updateUsersbyUsername(user.getUsername(), passwordEncryptor.encryptPassword(user.getPassword()), user.getUser_first_name(), user.getUser_last_name(), user.getUser_img(), user.getUser_email());
            return usersDao.findAllUsersbyUsername(user.getUsername());
        }


    }

    public Users checkEmail(String email) {

        Users checkuser = usersDao.findAllUsersbyEmail(email);

        if(checkuser == null){
            return null;
        }else{

            //email the user with premade password here

            checkuser.setPassword("P@ssW0Rd!");

            updateUser(checkuser);

        }

        return checkuser;
    }
}
