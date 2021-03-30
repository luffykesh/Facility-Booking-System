package com.csci5308.g17.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VerifyUser implements IVerifyUser {
    @Override
    public List getUserList(List<User> user) {
        List<String> userEmailList= new ArrayList<>();
        for (User u : user) {
            String email=u.getEmail();
            userEmailList.add(email);
        }
        return userEmailList;
    }
}
