package com.csci5308.g17;

import com.csci5308.g17.auth.CurrentUserService;
import com.csci5308.g17.user.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin/home")
    public ModelAndView adminHome() {
        User currentUser = CurrentUserService.getInstance().getCurrentUser();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin");
        mav.addObject("user", currentUser);
        return mav;
    }
}
