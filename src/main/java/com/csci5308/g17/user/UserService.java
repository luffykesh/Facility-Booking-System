package com.csci5308.g17.user;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;

@Service
public class UserService implements IUserService {

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

    private String encodePassword(String rawPassword) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword;

        if(rawPassword == null) {
            return null;
        }
        encodedPassword = encoder.encode(rawPassword);
        return encodedPassword;
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
    public void addUser(User user) {
        user.setPassword(encodePassword(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public void addAll(List<User> users) {
        for(User u : users) {
            addUser(u);
        }
    }

    // from spring's IUserService interface
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = userRepo.getUserByEmail(username);
        if(dbUser==null) {
            throw new UsernameNotFoundException(
                String.format("User: %s not found", username));
        }
        if(dbUser.getVerified() == false) {
            throw new UsernameNotFoundException(
                String.format("User: %s not verified", username));
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(dbUser.getRole());
        UserDetails user = new org.springframework.security.core.userdetails.User(
            dbUser.getEmail(),
            dbUser.getPassword(),
            Collections.singletonList(authority)
        );
        return user;
    }

    @Override
    public String setUserToken(String mailId) throws UserNotFoundException {
        User user = getUserByEmail(mailId);
        String token = RandomString.make(10);
        if (user == null) {
            throw new UserNotFoundException("No user found with the email " + mailId);
        }
        userRepo.setUserToken(mailId, token);
        return token;
    }

    @Override
    public User getUserByToken(String token) {
        User user = userRepo.getUserByToken(token);
        return user;
    }

    @Override
    public void updatePassword(Integer userId, String rawPassword) {
        if(rawPassword == null || rawPassword.length() == 0 ) {
            return;
        }
        String encodedPassword = encodePassword(rawPassword);
        userRepo.updatePassword(userId, encodedPassword);
    }

    @Override
    public void clearUserToken(Integer userId) {
        userRepo.clearUserToken(userId);
    }

    @Override
    public void verifyUser(Integer userId){
        userRepo.setVerifiedFlag(userId, true);
    }

    @Override
    public void save(User userForm) {
        User user=new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setRole(userForm.getRole());
        user.setBannerId(userForm.getBannerId());

        this.userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers(){
        List<User> user=this.userRepo.getAllUsers();
        return user;

    }

    @Override
    public void deleteUser(int id) {
        userRepo.deleteUser(id);
    }
}
