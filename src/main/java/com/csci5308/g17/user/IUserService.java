package com.csci5308.g17.user;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    long getUserCount();

    User getUserByEmail(String email);

    User getUserById(Integer Id);

    void addUser(User user);

    void addAll(List<User> users);

    String setUserToken(String mailId) throws UserNotFoundException;

    User getUserByToken(String token);

    void updatePassword(Integer userId, String rawPassword);
}
