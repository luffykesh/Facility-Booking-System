package com.csci5308.g17.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;


@Controller
public class ResetPasswordController {
    @GetMapping("/Forget_Password_Form")
    public String forgetPassword() {
        return "/Forget_Password_Form";

    }

    ResetPasswordService service;
    ResetPasswordService mail;


    public ResetPasswordController(ResetPasswordService service, ResetPasswordService mail) {
        this.service = service;
        this.mail = mail;
    }

    @PostMapping("/Forget_Password")
    public String processpassword(@RequestParam(name="email") String email,Model model) throws UserNotFoundException, MessagingException {
       int token = (int) (Math.random()*100);
       String password="12345";
       service.setEmail(email);
       service.check_email(email,token);
       String resetpassword="reset_password_form?token="+token;
       mail.email(email,resetpassword);
       service.updatePassword(token,password);

               model.addAttribute("message","Password reset link has been sent to your email");
    return "/Forget_Password_Form";
    }

    @GetMapping("/reset_password/{token}")
    public String getForm(@PathVariable("token") String token, Model model) {
        service.check_token(Integer.parseInt(token));
        model.addAttribute("token", token);
        return "reset_password_form";
    }

        @PostMapping("/reset_password_form")
    public String resetpassword(@RequestParam(value="token") int token,@RequestParam(value = "password") String password, Model model) {
        User user=service.check_token(token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
        else {
            service.updatePassword(token,password);
        }

        return "reset_password_form";
    }

}
