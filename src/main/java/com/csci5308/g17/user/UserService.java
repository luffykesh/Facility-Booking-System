package com.csci5308.g17.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private IUserRepository userRepo;

    public UserService(IUserRepository userRepo){
        this.userRepo = userRepo;
    }
    @Override
    public long getUserCount(){
        return this.userRepo.count();
    }
    @Override
    public User getUserByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }
    @Override
    public User getUserById(Integer Id) {
        return userRepo.getUserById(Id);
    }
    @Override
    public List<User> savetoDB(List<User> user) {
        List saveUser=userRepo.saveALL(user);
        return saveUser;

    }

}
