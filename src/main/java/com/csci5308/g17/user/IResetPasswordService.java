package com.csci5308.g17.user;

public interface IResetPasswordService {
    User check_email(String mailId,String token) throws UserNotFoundException;
    User check_token(String token);
    Integer updatePassword(String token, String password);
}
