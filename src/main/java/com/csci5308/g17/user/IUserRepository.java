package com.csci5308.g17.user;


import java.util.List;

public interface IUserRepository {
    User getUserById(Integer Id);
    User getUserByEmail(String email);
    Integer count();
    List<User> saveALL(List<User> user);
    Integer setTocken(String email, int token);
    Integer updatePassword(int token, String password);
    User getUserByToken(Integer token);
};
