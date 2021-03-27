package com.csci5308.g17.user;


import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class ResetPasswordService implements IResetPasswordService {
    private UserRepository userRepo;
    private User user;


    public ResetPasswordService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    //https://www.codejava.net/frameworks/spring-boot/spring-security-forgot-password-tutorial
    @Override
    public User checkEmail(String mailid, String token) throws UserNotFoundException {
        User l = userRepo.getUserByEmail(mailid);
        if (l != null) {
            userRepo.setToken(mailid, token);
        } else {
            throw new UserNotFoundException("No user found with the email " + mailid);
        }
        return l;
    }

    @Override
    public User checkToken(String token) {
        User user = userRepo.getUserByToken(token);
        return user;
    }

    @Override
    public Integer updatePassword(String token, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(password);
        int id = userRepo.getUserIdByToken(token);
        int user = userRepo.updatePassword(id, encryptedPassword);
        return user;
    }
}