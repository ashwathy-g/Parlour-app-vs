package com.example.ParlourApp.parlour;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
//import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    public void sendOtpEmail(String email,String otp)
    {
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Forgot Password OTP");
        mailMessage.setText("Your OTP is :" + otp);
        javaMailSender.send(mailMessage);
    }
}