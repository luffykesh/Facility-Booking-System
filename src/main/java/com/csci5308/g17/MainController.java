package com.csci5308.g17;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin_dashboard")
    public String getAdminDashboard(){
        return "admin_dashboard";
    }

    @GetMapping("manager_dashboard")
    public String getManagerDashboard(){
        return "manager_dashboard";
    }

    @GetMapping("/user_dashboard")
    public String getUserDashboard(){
        return "user_dashboard";
    }
}
