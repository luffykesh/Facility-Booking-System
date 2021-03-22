package com.csci5308.g17.user;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class ResetPasswordService {
    private UserRepository userRepo;
    private String email;
    User user;
    JavaMailSender javaMailSender ;

    public ResetPasswordService(UserRepository userRepo, JavaMailSender javaMailSender) {
        this.userRepo = userRepo;
        this.javaMailSender = javaMailSender;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //https://www.codejava.net/frameworks/spring-boot/spring-security-forgot-password-tutorial
    public User check_email(String mailid, int token) throws UserNotFoundException {
       User l= userRepo.getUserByEmail(mailid);
        if (l != null ) {
           userRepo.setTocken(mailid,token);


        } else {

            throw new UserNotFoundException("Could not find any customer with the email " + mailid);
        }

        return l;
    }

    public User check_token(int token){
        User u=userRepo.getUserByToken(token);
        return u;
    }

    public Integer updatePassword(int token, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(password);
        int user=userRepo.updatePassword(token,encryptedPassword);
        int t=0;
        userRepo.setTocken(email,t);
        return user;

    }

    public boolean email(String emailId, String link) throws MessagingException {

        MimeMessage new_mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(new_mail);
        String content ="<p>Hello,</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>";


        helper.setTo(emailId);
        helper.setText(content,true);
        javaMailSender.send(new_mail);


        return false;
    }
}