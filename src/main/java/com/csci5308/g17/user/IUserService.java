package com.csci5308.g17.user;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    User getUserByEmail(String email);

    User getUserById(Integer Id);

    void addUser(User user);

    void addAll(List<User> users);

    void save(User user);

    String setUserToken(String mailId) throws UserNotFoundException;

    User getUserByToken(String token);

    void updatePassword(Integer userId, String rawPassword);

    void clearUserToken(Integer userId);

    void verifyUser(Integer id);

    List<User> getAllUsers();

    public void deleteUser(int id);
}
