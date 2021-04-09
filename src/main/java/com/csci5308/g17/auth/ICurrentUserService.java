package com.csci5308.g17.auth;
import com.csci5308.g17.user.User;

public interface ICurrentUserService {

    User getCurrentUser();

    boolean isAdmin();

    boolean isManager();

    boolean isUser();
}
