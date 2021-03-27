package com.csci5308.g17.user;

public interface IResetPasswordService {
    User checkEmail(String mailId,String token) throws UserNotFoundException;
    User checkToken(String token);
    Integer updatePassword(String token, String password);
}
