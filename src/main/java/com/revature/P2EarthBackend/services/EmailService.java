package com.revature.P2EarthBackend.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class EmailService {

        Logger logger =Logger.getLogger(EmailService.class);

        @Autowired
        private JavaMailSender mailSender;

        /**
         * Will not return any Object or variable.
         * Sends email with the specified email passed through the parameter.
         *
         * @param user_email the email you want to use to send the password reset email to
         */
        public void sendEmail(String user_email) {

                String from = "planet.earth.reset@gmail.com";
                String to = user_email; //insert user_email
                String temp = "P@ssW0Rd!";

                SimpleMailMessage message = new SimpleMailMessage();

                message.setFrom(from);
                message.setTo(to);
                message.setSubject("Reset Password");
                message.setText("Temporary password: " + temp);

                mailSender.send(message);
                logger.info("send email" + message+"from" +from+"to"+to);

        }
}
