package com.csci5308.g17.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    IUserService userService;

    public UserController() {
        userService = UserService.getInstance();
    }

    @GetMapping("/count")
    public long getUserCount() {
        return userService.getUserCount();
    }
}
