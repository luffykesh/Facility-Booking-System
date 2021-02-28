package com.csci5308.g17.service;

import com.csci5308.g17.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepo;

    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public long getUserCount(){
        return this.userRepo.count();
    }
}
