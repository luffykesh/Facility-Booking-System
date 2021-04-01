package com.csci5308.g17.user;

import com.csci5308.g17.config.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements IEmailService {
    private static EmailService instance;
    JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public static EmailService getInstance() {
        ApplicationContext context = ApplicationContextProvider.getContext();
        if (instance == null) {
            instance = new EmailService(context.getBean(JavaMailSender.class));
        }
        return instance;
    }

    public void sendResetPasswordEmail(String emailId, String token, String formLink) throws MessagingException {

        MimeMessage new_mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(new_mail);
        String content = String.format("<p>Hello,</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"%s\">Change password</a></p>", formLink);
        helper.setTo(emailId);
        helper.setText(content, true);
        javaMailSender.send(new_mail);
    }
}
