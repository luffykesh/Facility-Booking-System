package com.csci5308.g17.user;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import net.bytebuddy.utility.RandomString;
import java.util.Random;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;


@Controller
public class ResetPasswordController {
    private  ResetPasswordService service;

    @GetMapping("/Forget_Password_Form")
    public String forgetPassword() {
        return "/Forget_Password_Form";

    }

    public ResetPasswordController(ResetPasswordService service) {
        this.service = service;

    }


    @PostMapping("/Forget_Password")
    public String processpassword(@RequestParam(name="email") String email,Model model) throws UserNotFoundException, MessagingException {


        String token = RandomString.make(10);
        service.check_email(email,token);
        String resetpassword="http://localhost:8080/reset_password_form/"+token;
        service.email(email,resetpassword);
        model.addAttribute("message","Password reset link has been sent to your email");
        return "/Forget_Password_Form";

    }

    @GetMapping("/reset_password_form/{token}")
    public String getForm(@PathVariable("token") String token, Model model) {
        User user=service.check_token(token);
        if (user == null) {
            model.addAttribute("message", "Invalid Token");
        }
        return "reset_password_form";
    }

    @PostMapping("/reset_password_form")
    public String resetpassword(@RequestParam(value="token") String token, @RequestParam(value = "password") String password, Model model) {
        User user=service.check_token(token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "reset_password_form";
        }
        else {
            service.updatePassword(token,password);
            model.addAttribute("message", "Password Reset Successful");
        }

        return "reset_password_form";
    }

}
