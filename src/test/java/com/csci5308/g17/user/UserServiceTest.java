package com.csci5308.g17.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {

    @Test
    void getUserCountTest() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);

        final Integer USER_COUNT = 10;

        Mockito.when(userRepository.count()).thenReturn(USER_COUNT);
        Assertions.assertEquals(userService.getUserCount(), new Long(USER_COUNT));
    }

    @Test
    void getUserByEmailTest() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);

        final String EMAIL="email";

        User dbUser = new User();
        dbUser.setEmail(EMAIL);
        dbUser.setId(100);
        dbUser.setName("name");
        dbUser.setPassword("password");
        dbUser.setRole("role");
        dbUser.setVerified(true);

        Mockito.when(userRepository.getUserByEmail(EMAIL)).thenReturn(dbUser);
        Assertions.assertTrue(userService.getUserByEmail(EMAIL).equals(dbUser));
    }

    @Test
    void getUserByIdTest() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);

        final Integer userId = 100;
        User dbUser = new User();
        dbUser.setId(userId);
        dbUser.setEmail("email");
        dbUser.setName("name");
        dbUser.setPassword("password");
        dbUser.setRole("role");
        dbUser.setVerified(true);

        Mockito.when(userRepository.getUserById(userId)).thenReturn(dbUser);

        User returnedUser = userService.getUserById(userId);
        Assertions.assertNotNull(returnedUser);
        Assertions.assertTrue(returnedUser.equals(dbUser));
    }

    @Test
    void loadUserByUsernameTest() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);

        final String EMAIL="user@host.com";

        User dbUser = new User();
        dbUser.setEmail(EMAIL);
        dbUser.setId(100);
        dbUser.setName("name");
        dbUser.setPassword("password");
        dbUser.setRole(UserConstants.USER_ROLE_ADMIN);
        dbUser.setVerified(true);

        Mockito.when(userRepository.getUserByEmail(EMAIL)).thenReturn(dbUser);
        Assertions.assertNotNull(userService.loadUserByUsername(EMAIL));
    }

    @Test
    void loadUserByUsername_UsernameNotFoundException_Test() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);

        final String JANE_DOE_EMAIL = "jane.doe@host.com";

        Mockito.when(userRepository.getUserByEmail(JANE_DOE_EMAIL)).thenThrow(UsernameNotFoundException.class);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(JANE_DOE_EMAIL);
        });
    }

    @Test
    public void savetoDB(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService service=new UserService(userRepository);
        User dbUser = new User();
        dbUser.email="email";
        dbUser.name="user1";
        dbUser.password="password";
        dbUser.role="user";
        dbUser.bannerId="B00868907";
        List<User> user=new ArrayList<>();
        user.add(dbUser);
        Boolean check=true;
        Mockito.when(userRepository.saveALL(user)).thenReturn(user);
        List returnedUser = service.savetoDB(user);
        Assertions.assertTrue(returnedUser.equals(user));
    }
}
