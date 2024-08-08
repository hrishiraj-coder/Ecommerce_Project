package com.ma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;


    public void sendSimpleMail(String toEmail,String subject,String body){ 
        SimpleMailMessage message=new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("no-replay@tradzy.com");


        javaMailSender.send(message);
    }
}
