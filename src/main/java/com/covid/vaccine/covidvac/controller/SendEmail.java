package com.covid.vaccine.covidvac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


public class SendEmail implements  Runnable {



    String toEmailId;

    String message;

    SendEmail(String toEmailId, String message){
        this.toEmailId = toEmailId;
        this.message = message;

    }


    @Value("${spring.mail.from}")
    String from;
    /*
    @Value("${spring.mail.host}")
    private String host;



    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.username}")
    private String userName;
*/

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void run() {
       /* JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(userName);
        mailSender.setPassword(password);*/


        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(toEmailId);
        message.setSubject("");
        message.setText("Deary guys! This is a plain text email.");
        mailSender.send(message);


    }
}
