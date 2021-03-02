package com.csci5308.g17.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserCountController {

    UserService userService;

    public UserCountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/count")
    public long getUserCount(){
        return this.userService.getUserCount();
    }
}
