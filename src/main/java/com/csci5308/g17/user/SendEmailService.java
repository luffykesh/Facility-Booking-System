package com.csci5308.g17.user;

import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {
    JavaMailSender javaMailSender;

    public SendEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender=javaMailSender;
    }

    public void sendResetPasswordEmail(String emailId, String token) throws MessagingException {

        MimeMessage new_mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(new_mail);
        String resetpassword="http://localhost:8080/Reset_Password_Form/" + token;
        String content ="<p>Hello,</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + resetpassword + "\">Change password</a></p>";
        helper.setTo(emailId);
        helper.setText(content,true);
        javaMailSender.send(new_mail);
    }
}
