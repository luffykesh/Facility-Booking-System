package com.csci5308.g17.user;

import java.util.List;

public interface IUserService {
    long getUserCount();
    User getUserByEmail(String email);
    User getUserById(Integer Id);
    List<User> savetoDB(List<User> user);
}
