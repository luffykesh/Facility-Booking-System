package com.csci5308.g17.user;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

    private IUserService userService;
    private IUserCSVParser csvService;
    private IEmailService emailService;

    public UserController(UserCSVParser csvService, EmailService emailService) {
        userService = UserService.getInstance();
        this.csvService = csvService;
        this.emailService = emailService;
    }

    @GetMapping("/admin/user_upload")
    public String userCSVUploadForm(){
        return "upload";
    }

    @PostMapping("/admin/user_upload")
    public String userCSVData(@RequestParam(name="file") MultipartFile file) throws IOException {
        if(csvService.isCSVFormat(file)) {
            List<User> userList = csvService.readCSV(file.getInputStream());
            userService.addAll(userList);
        }
        return "redirect:/admin/home";
    }

    @GetMapping("/forgot_password")
    public String getForgetPasswordForm() {
        return "Forgot_Password_Form";
    }

    @PostMapping("/forgot_password")
    public String processPassword(@RequestParam(name="email") String email, Model model, HttpServletRequest request) {
        String serverUrl = String.format(
            "%s://%s:%d",
            request.getScheme(), request.getServerName(), request.getServerPort());
        String resetPasswordLink;
        try {
            String token = userService.setUserToken(email);
            resetPasswordLink = String.format("%s/reset_password/%s", serverUrl, token);
            emailService.sendResetPasswordEmail(email, token, resetPasswordLink);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        model.addAttribute("message", "Password reset link has been sent to your email");
        return "Forgot_Password_Form";
    }

    @GetMapping("/reset_password/{token}")
    public String getResetPasswordForm(@PathVariable("token") String token, Model model) {
        User user=userService.getUserByToken(token);
        if (user == null) {
            model.addAttribute("message", "Invalid Token");
        }
        return "Reset_Password_Form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(@RequestParam(value="token") String token, @RequestParam(value = "password") String password, Model model) {
        User user=userService.getUserByToken(token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "Reset_Password_Form";
        }
        else {
            userService.updatePassword(user.getId(), password);
            model.addAttribute("message", "Password Reset Successful");
        }
        return "Reset_Password_Form";
    }

}
