package com.csci5308.g17;

import java.security.Principal;

import com.csci5308.g17.user.User;
import com.csci5308.g17.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin/home")
    public ModelAndView adminHome(Principal principal) {
        User u = userService.getUserByEmail(principal.getName());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin");
        mav.addObject("user", u);
        return mav;
    }
}
