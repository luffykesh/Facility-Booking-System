package com.csci5308.g17.user;


import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService implements IResetPasswordService {
    private UserRepository userRepo;
    private User user;

    public ResetPasswordService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    //https://www.codejava.net/frameworks/spring-boot/spring-security-forgot-password-tutorial
    @Override
    public String setUserToken(String mailid) throws UserNotFoundException {
        User user = userRepo.getUserByEmail(mailid);
        String token = RandomString.make(10);
        if (user != null) {
            userRepo.setToken(mailid, token);
        }
        else {
            throw new UserNotFoundException("No user found with the email " + mailid);
        }
        return token;
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