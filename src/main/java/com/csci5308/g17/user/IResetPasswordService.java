package com.csci5308.g17.user;

public interface IResetPasswordService {
    User check_token(int token);
    Integer updatePassword(int token, String password);
    User check_email(String mailid, int token) throws UserNotFoundException;
}
