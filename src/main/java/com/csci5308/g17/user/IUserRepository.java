package com.csci5308.g17.user;

import java.util.List;

public interface IUserRepository {
    User getUserById(Integer Id);

    User getUserByEmail(String email);

    User getUserByToken(String token);

    void save(User u);

    void setUserToken(String email, String token);

    void updatePassword(Integer id, String password);

    void clearUserToken(Integer userId);

    void setVerifiedFlag(Integer userId, Boolean flag);

    List<User> getAllUsers();

    void deleteUser(Integer id);
};
