package com.csci5308.g17.user;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    long getUserCount();

    User getUserByEmail(String email);

    User getUserById(Integer Id);

    List<User> savetoDB(List<User> user);
}
