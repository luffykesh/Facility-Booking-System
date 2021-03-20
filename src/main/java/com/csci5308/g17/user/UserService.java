package com.csci5308.g17.user;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private IUserRepository userRepo;
    private static UserService instance;

    public UserService(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserService(UserRepository.getInstance());
        }
        return instance;
    }

    public long getUserCount() {
        return this.userRepo.count();
    }

    public User getUserByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }

    public User getUserById(Integer Id) {
        return userRepo.getUserById(Id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = userRepo.getUserByEmail(username);
        if(dbUser==null) {
            throw new UsernameNotFoundException(
                String.format("User: %s not found", username));
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(dbUser.getRole());
        UserDetails user = new org.springframework.security.core.userdetails.User(
            dbUser.getEmail(),
            dbUser.getPassword(),
            Collections.singletonList(authority)
        );
        return user;
    }
}
