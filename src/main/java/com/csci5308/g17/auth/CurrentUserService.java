package com.csci5308.g17.auth;

import com.csci5308.g17.user.IUserService;
import com.csci5308.g17.user.User;
import com.csci5308.g17.user.UserConstants;
import com.csci5308.g17.user.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService implements ICurrentUserService {

    private IUserService userService;
    private static CurrentUserService instance;

    public CurrentUserService(IUserService userService) {
        this.userService = userService;
    }

    public static CurrentUserService getInstance() {
        if(instance == null) {
            instance = new CurrentUserService(UserService.getInstance());
        }
        return instance;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = null;
        if (authentication.isAuthenticated()) {
            UserDetails springUser = (UserDetails) authentication.getPrincipal();
            currentUser = userService.getUserByEmail(springUser.getUsername());
        }
        return currentUser;
    }

    public boolean isAdmin() {
        User currentUser = getCurrentUser();
        return currentUser.getRole().equals(UserConstants.USER_ROLE_ADMIN);
    }

    public boolean isManager() {
        User currentUser = getCurrentUser();
        return currentUser.getRole().equals(UserConstants.USER_ROLE_MANAGER);
    }
    public boolean isUser() {
        User currentUser = getCurrentUser();
        return currentUser.getRole().equals(UserConstants.USER_ROLE_USER);
    }
}
