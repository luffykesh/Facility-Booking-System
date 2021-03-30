package com.csci5308.g17.user;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordEmailService implements IEmailService {
    JavaMailSender javaMailSender;

    public ResetPasswordEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender=javaMailSender;
    }

    public void sendEmail(String emailId, String token, String formLink) throws MessagingException {

        MimeMessage new_mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(new_mail);
        String content = String.format("<p>Hello,</p>"
            + "<p>Click the link below to change your password:</p>"
            + "<p><a href=\"%s\">Change password</a></p>", formLink);
        helper.setTo(emailId);
        helper.setText(content,true);
        javaMailSender.send(new_mail);
    }
}
