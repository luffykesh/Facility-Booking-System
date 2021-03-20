package com.csci5308.g17.user;


import java.util.List;

public interface IUserRepository {
    User getUserById(Integer Id);
    User getUserByEmail(String email);
    Integer count();
    void saveALL(List<User>u);
};
