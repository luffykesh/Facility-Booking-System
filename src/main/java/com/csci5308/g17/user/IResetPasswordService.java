package com.csci5308.g17.user;

public interface IResetPasswordService {
    String setUserToken(String mailid)throws UserNotFoundException;
    User checkToken(String token);
    Integer updatePassword(String token, String password);
}
