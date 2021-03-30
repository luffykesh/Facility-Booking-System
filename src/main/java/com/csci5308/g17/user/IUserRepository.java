package com.csci5308.g17.user;

public interface IUserRepository {
    User getUserById(Integer Id);

    User getUserByEmail(String email);

    Integer count();

    User getUserByToken(String token);

    void save(User u);

    void setUserToken(String email, String token);

    void updatePassword(Integer id, String password);

    void clearUserToken(Integer userId);
    void verifyUser(int id,int verify);

};
