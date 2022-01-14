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

    /**
     * Returns a Users Object that can be then used.
     * If no user has specified ID then will return null.
     *
     * @param user_id   the ID of the user you want to receive from the Database
     * @return          the user with the specified ID or null
     */
    public Users getOneUser(Integer user_id){
        Users user = this.usersDao.getById(user_id);

        if (user.getUserId() == null) return null;

        logger.info("get one user " +user);

        return user;
    }

    /**
     * Method used for checking the users Object that is received from the database.
     * If there is no user Object with specified username. The method will return null.
     *
     * @param username  the username of the user you want to receive from the database
     * @return          the user with the specified username or null
     */
    public Users getOneUserByUsername(String username){
        Users user = this.usersDao.findAllUsersbyUsername(username);

        if (user.getUserId() == null) return null;

        return user;
    }


    /**
     * Method for creating a user to be saved onto the database.
     * If username or email is already in use, the method will return null.
     *
     * @param user  a user Object you would like to create and save onto the database
     * @return      the user created or null
     */
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

    /**
     * Method used to verify if a username is currently being used within the database.
     * If used, then will return that user.
     * If not in use, then will return null.
     *
     * @param username  the username you would like to verify within the database
     * @return          the user Object with the specified username
     */
    public Users validateUserByUsername(String username){
        Users user = usersDao.findAllUsersbyUsername(username);

        logger.info("validate user by user name " +username);

        return user;
    }

    /**
     * Method used verify login credentials.
     * Will decode password and verify if credentials match.
     *
     * @param loginInfo an Object that contains the user's login credentials
     * @return          the user with the specified loginInfo/Credentials
     */
    public Users loginUser(LoginInfo loginInfo) {

        Users checkuser = usersDao.findAllUsersbyUsername(loginInfo.getUsername());
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        logger.info("user login"+loginInfo);

        if(checkuser == null){
            return null;
        }else{

            boolean passCheck = passwordEncryptor.checkPassword(loginInfo.getPassword(), checkuser.getPassword());

            if(passCheck){
                return checkuser;

            }else{
                return null;
            }

        }

    }

    /**
     * Returns an updated users Object that will be adjusted within the database.
     * If username or email is already in use by another user, will return users Object.
     * with userId of -1 or -2.
     *
     * @param user          specified user Object with changed information
     * @param user_img      specified user_Img to be displayed on user account
     * @return              the users Object with adjusted information or users Object with userId of -1 or -2
     * @throws IOException  for the upload service method
     */
    public Users updateUser(Users user, MultipartFile user_img) throws IOException {

        Users checkuser = usersDao.findById(user.getUserId()).orElse(null);

        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        System.out.println("Update User Ran");

        if(checkuser.getUserId() == null){
            System.out.println("DEBUG: user: " + user);
            return null;
        }else {

            if (usersDao.findAllUsersBySpecificUsername(checkuser.getUsername(),user.getUsername()) != null) {
                System.out.println("Username error");
                checkuser.setUser_img("-1");
                return checkuser;
            }

            if (usersDao.findAllUsersBySpecificEmail(checkuser.getUser_email(), user.getUser_email()) != null) {
                logger.info("Email is already registered");
                checkuser.setUser_img("-2");
                return checkuser;
            }

            System.out.println("Update User Ran");
            //upload new photo to S3 and store it to database
            String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword()); //encrypting the password
            user.setUserId(checkuser.getUserId());
            user.setPassword(encryptedPassword); //setting the new encrypted password in the object

            String url = uploadService.uploadMultiFile(user_img, user.getUsername() + "ProfileImg");
            user.setUser_img(url);

            System.out.println("DEBUG: user: " + user);

            logger.info("update user "+user);

            return this.usersDao.save(user);
        }


    }

    /**
     * Returns the users Object with specified email attached to it.
     * If no user has specified email, will return null.
     *
     * @param email email string to search for specified user within database
     * @return      users Object with the specified email or null
     */
    public Users resetPassword(String email) {
        System.out.println("FROM FRONT END: " + email);
        Users checkuser = usersDao.findAllUsersbyEmail(email);

        if(checkuser.getUser_email() == null){
            return null;
        }else{

            BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
            String encryptedPassword = passwordEncryptor.encryptPassword("P@ssW0Rd!");

            checkuser.setPassword(encryptedPassword);

            this.usersDao.save(checkuser);


        }
        logger.info("reset password by email" + email);

        return checkuser;
    }

    /**
     * Returns boolean value if email is within.
     * Will return true if email passed is unique within the database.
     * Will return false if email passed is not unique within the database.
     *
     * @param email email string to verify within the database
     * @return      true or false
     */
    public Boolean isEmailUnique (String email) {

        Users users = usersDao.findAllUsersbyEmail(email);

        if (users != null) return false;

        return true;
    }

}
