package com.csci5308.g17.user;

public class UserNotFoundException extends Exception {

    static final long serialVersionUID = 12484953863596L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
