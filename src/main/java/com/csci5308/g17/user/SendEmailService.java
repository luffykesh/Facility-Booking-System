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

    public void sendEmail(String emailId, String link) throws MessagingException {

        MimeMessage new_mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(new_mail);
        String content ="<p>Hello,</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change password</a></p>";
        try {
            helper.setTo(emailId);
            helper.setText(content,true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(new_mail);

    }
}
