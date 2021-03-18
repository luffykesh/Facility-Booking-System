package com.csci5308.g17.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private IUserRepository userRepo;

    public UserService(IUserRepository userRepo){
        this.userRepo = userRepo;
    }

    public long getUserCount(){
        return this.userRepo.count();
    }
    public User getUserByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }
    public User getUserById(Integer Id) {
        // TODO
        return null;
    }
}
