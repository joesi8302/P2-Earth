package com.revature.P2EarthBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmailController {

        @Autowired
        private JavaMailSender mailSender;

        public void sendEmail() {

                String from = "planet.earth.reset@gmail.com";
                String to = user_email; //insert user_email
                String temp = "Ch@ng3me123"

                SimpleMailMessage message = new SimpleMailMessage();

                message.setFrom(from);
                message.setTo(to);
                message.setSubject("Reset Password");
                message.setText("Temporary password: " + temp);

                mailSender.send(message);

        }
}