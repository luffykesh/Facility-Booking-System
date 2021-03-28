package com.csci5308.g17.user;

import java.util.List;

public interface IUserRepository {
    User getUserById(Integer Id);
    User getUserByEmail(String email);
    Integer count();
    User getUserByToken(String token);
    List<User> saveALL(List<User>u);
    Integer setToken(String email, String token);
    int updatePassword(int id, String password);
    Integer getUserIdByToken(String token);
};
