package com.revature.P2EarthBackend.services;

import com.revature.P2EarthBackend.models.LoginInfo;
import com.revature.P2EarthBackend.models.Users;
import com.revature.P2EarthBackend.repository.UsersDao;
import lombok.Data;
import org.apache.log4j.Logger;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
@Data
public class UsersService {

    private UsersDao usersDao;
    private EmailService emailService;
    private JavaMailSender mailSender;
    private UploadService uploadService;

    Logger logger =Logger.getLogger(UsersService.class);

    @Autowired
    public UsersService(UsersDao usersDao, UploadService uploadService){
        this.usersDao = usersDao;
        this.uploadService = uploadService;
    }
    public UsersService(UsersDao usersDao){
        this.usersDao=usersDao;
    }

    public List<Users> getAllUsers(){
        List<Users> usersList = this.usersDao.findAll();

        if (usersList == null)
            return null;

        logger.info("get all users " +usersList);

        return usersList;
    }

    public Users getOneUser(Integer user_id){
        Users user = this.usersDao.getById(user_id);

        if (user.getUser_id() == null) return null;

        logger.info("get one user " +user);

        return user;
    }

    public Users getOneUserByUsername(String username){
        Users user = this.usersDao.findAllUsersbyUsername(username);

        if (user.getUser_id() == null) return null;

        return user;
    }

    public Users createUser(Users user){

        Users user1 = usersDao.findAllUsersbyUsername(user.getUsername());

        if (user1 != null) { // if not null that means it finds something

            return null;
        }else { //if null that means it didnt find a username and we can add a username

            BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
            String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword()); //encrypting the password

            user.setPassword(encryptedPassword); //setting the new encrypted password in the object

            logger.info("create a new user "+ user);

            return this.usersDao.save(user); //saves the

        }
    }

    public Users validateUserByUsername(String username){
        Users user = usersDao.findAllUsersbyUsername(username);

        logger.info("validate user by user name " +username);

        return user;
    }

    public void deleteUser(Integer user_id){
         usersDao.deleteById(user_id);
    }

    public Users loginUser(LoginInfo loginInfo) {

        Users checkuser = usersDao.findAllUsersbyUsername(loginInfo.getUsername());
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        boolean passCheck = passwordEncryptor.checkPassword(loginInfo.getPassword(), checkuser.getPassword());

        logger.info("user login"+loginInfo);


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

    public Users updateUser(Users user, MultipartFile user_img) throws IOException {

        Users checkuser = usersDao.findById(user.getUser_id()).orElse(null);

        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        System.out.println("Update User Ran");

        if(checkuser.getUser_id() == null){
            System.out.println("DEBUG: user: " + user);
            return null;
        }else{
            //todo if username is still the same when updating, it causes issues
            if (usersDao.findAllUsersBySpecificUsername(checkuser.getUsername(),user.getUsername()) != null) {
                System.out.println("Username error");
                return null;
            }
            System.out.println("Update User Ran");
            //upload new photo to S3 and store it to database
            String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword()); //encrypting the password
            user.setUser_id(checkuser.getUser_id());
            user.setPassword(encryptedPassword); //setting the new encrypted password in the object

            String url = uploadService.uploadMultiFile(user_img, user.getUsername() + "ProfileImg");
            user.setUser_img(url);

            System.out.println("DEBUG: user: " + user);

            logger.info("update user "+user);

            return this.usersDao.save(user);
        }


    }

    public Users resetPassword(String email) {
        System.out.println("FROM FRONT END: " + email);
        Users checkuser = usersDao.findAllUsersbyEmail(email);


        if(checkuser.getUser_email() == null){
            return null;
        }else{

            checkuser.setPassword("P@ssW0Rd!");

            this.usersDao.save(checkuser);


        }
        logger.info("reset password by email" + email);

        return checkuser;
    }

}
