package com.csci5308.g17.user;


public interface IUserRepository {
    User getUserById(Integer Id);
    User getUserByEmail(String email);
    Integer count();
};
