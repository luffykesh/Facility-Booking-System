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
        ResetPasswordService service = new ResetPasswordService(userRepository);

        final String TOKEN="53";

        User dbUser = new User();
        dbUser.setEmail("email");
        dbUser.setId(100);
        dbUser.setName("name");
        dbUser.setPassword("password");
        dbUser.setRole("role");
        dbUser.setVerified(true);
        dbUser.setToken(TOKEN);

        Mockito.when(userRepository.getUserByToken(TOKEN)).thenReturn(dbUser);
        assertTrue(service.checkToken(TOKEN).equals(dbUser));
    }

    @Test
    void updatePassword(){
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        ResetPasswordService service = new ResetPasswordService(userRepo);
        final int rows_updated=1;
        final String TOKEN="abs";
        final int ID=0;
        final String PASSWORD="1234";
        Mockito.when(userRepo.updatePassword(ID,PASSWORD)).thenReturn(rows_updated);
        Assertions.assertEquals(service.updatePassword(TOKEN,PASSWORD),0);
    }

}