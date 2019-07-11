package com.mk.donations.repository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

@Repository
public class MailSenderRepository {

    JavaMailSender mailSender;

    public MailSenderRepository(JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
    }

    public void sentMail(String to, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }
}
