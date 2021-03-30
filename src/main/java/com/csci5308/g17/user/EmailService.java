package com.csci5308.g17.user;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender=javaMailSender;
    }

    public void sendEmail(String emailId, String token, String formLink,String emailContent) throws MessagingException {

        MimeMessage new_mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(new_mail);
        String content = String.format(emailContent, formLink);
        helper.setTo(emailId);
        helper.setText(content,true);
        javaMailSender.send(new_mail);
    }
}
