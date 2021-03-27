package com.csci5308.g17.user;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import javax.mail.MessagingException;


@Controller
public class ResetPasswordController {
    private ResetPasswordService service;
    private SendEmailService emailService;

    public ResetPasswordController(ResetPasswordService service,SendEmailService emailService) {
        this.service = service;
        this.emailService=emailService;
    }

    @GetMapping("/Forgot_Password_Form")
    public String getForgetPasswordForm() {
        return "Forgot_Password_Form";
    }

    @PostMapping("/Forgot_Password")
    public String processPassword(@RequestParam(name="email") String email,Model model)  {
        try {
            String getToken=service.setUserToken(email);
            emailService.sendEmail(email,getToken);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        model.addAttribute("message","Password reset link has been sent to your email");
        return "Forgot_Password_Form";
    }

    @GetMapping("/Reset_Password_Form/{token}")
    public String getResetPasswordForm(@PathVariable("token") String token, Model model) {
        User user=service.checkToken(token);
        if (user == null) {
            model.addAttribute("message", "Invalid Token");
        }
        return "Reset_Password_Form";
    }

    @PostMapping("/Reset_Password_Form")
    public String processResetPassword(@RequestParam(value="token") String token, @RequestParam(value = "password") String password, Model model) {
        User user=service.checkToken(token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "Reset_Password_Form";
        }
        else {
            service.updatePassword(token,password);
            model.addAttribute("message", "Password Reset Successful");
        }
        return "Reset_Password_Form";
    }
}
