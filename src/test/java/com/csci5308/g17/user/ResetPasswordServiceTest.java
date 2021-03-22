package com.csci5308.g17.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;

class ResetPasswordServiceTest {
    @Test
    void getUserByToken() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        JavaMailSender mailSender = null;
        ResetPasswordService service = new ResetPasswordService(userRepository,mailSender);

        final Integer TOKEN=53;


        User dbUser = new User();
        dbUser.setEmail("email");
        dbUser.setId(100);
        dbUser.setName("name");
        dbUser.setPassword("password");
        dbUser.setRole("role");
        dbUser.setVerified(true);
        dbUser.setToken(TOKEN);

        Mockito.when(userRepository.getUserByToken(TOKEN)).thenReturn(dbUser);
        assertTrue(service.check_token(TOKEN).equals(dbUser));
    }
    @Test
    void getUserByEmail() throws UserNotFoundException {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        JavaMailSender mailSender = null;
        ResetPasswordService service = new ResetPasswordService(userRepository,mailSender);

        final Integer TOKEN=53;
        final String EMAIL="email";


        User dbUser = new User();
        dbUser.setEmail(EMAIL);
        dbUser.setId(100);
        dbUser.setName("name");
        dbUser.setPassword("password");
        dbUser.setRole("role");
        dbUser.setVerified(true);
        dbUser.setToken(TOKEN);

        Mockito.when(userRepository.getUserByEmail(EMAIL)).thenReturn(dbUser);
        assertTrue(service.check_email(EMAIL,TOKEN).equals(dbUser));
    }
    @Test
    void updatePassword(){
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        JavaMailSender mailSender = null;
        ResetPasswordService service = new ResetPasswordService(userRepo,mailSender);

        final Integer TOKEN=53;
        final String PASSWORD="1234";
        Mockito.when(userRepo.updatePassword(TOKEN,PASSWORD)).thenReturn(0);
        Assertions.assertEquals(service.updatePassword(TOKEN,PASSWORD),0);

    }

}