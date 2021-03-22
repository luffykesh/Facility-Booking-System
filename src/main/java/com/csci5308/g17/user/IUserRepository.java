package com.csci5308.g17.user;


import java.util.List;

public interface IUserRepository {
    User getUserById(Integer Id);
    User getUserByEmail(String email);
    Integer count();
    User getUserByToken(Integer token);
    List<User> saveALL(List<User>u);
    Integer setTocken(String email, int token);
    Integer updatePassword(int token, String password);
};
