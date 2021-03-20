package com.csci5308.g17.user;

import com.csci5308.g17.config.DatabaseConfig;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserCountController {

    UserService userService;

    public UserCountController() {
        userService = UserService.getInstance();
    }

    @GetMapping("/count")
    public long getUserCount() {
        return userService.getUserCount();
    }
}
