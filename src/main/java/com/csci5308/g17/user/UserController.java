package com.csci5308.g17.user;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

    private IUserService userService;
    private IUserCSVParser csvService;
    private IEmailService emailService;
    private UserConstants userConstants;

    public UserController(UserCSVParser csvService, EmailService emailService, List emailList,UserConstants userConstants) {
        userService = UserService.getInstance();
        this.csvService = csvService;
        this.emailService = emailService;
        this.userConstants=userConstants;
    }

    private void addAndEmailUser(String email,HttpServletRequest request,String content,String link){
        String serverUrl=getURL(request);
        try {
            String token = userService.setUserToken(email);
            String resetPasswordLink = String.format("%s/%s/%s", serverUrl,link,token);
            String emailContent=String.format(content, resetPasswordLink);
            emailService.sendEmail(email,emailContent);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getURL(HttpServletRequest request){
        String serverUrl = String.format(
                "%s://%s:%d",
                request.getScheme(), request.getServerName(), request.getServerPort());
        return serverUrl;
    }

    @GetMapping("/admin/user_upload")
    public String userCSVUploadForm(){
        return "upload";
    }

    @PostMapping("/admin/user_upload")
    public String userCSVData(@RequestParam(name="file") MultipartFile file,HttpServletRequest request,Model model) throws IOException {
        if(csvService.isCSVFormat(file)) {
            List<User> userList = csvService.readCSV(file.getInputStream());
            userService.addAll(userList);
            String link="verification_form";
            String content="<p>Hello,</p>"
                    + "<p>Click the link below to verify your account:</p>"
                    + "<p><a href=\"%s\">Account Verification</a></p>";
            List<String> userEmails = userList.stream().map((user) -> user.getEmail()).collect(Collectors.toList());
            for(int i=0;i<userEmails.size();i++) {
                addAndEmailUser(userEmails.get(i),request,content,link);
            }
        }
        return "redirect:/admin/home";
    }

    @GetMapping("/verification_form/{token}")
    public String getVerificationForm(@PathVariable("token") String token, Model model) {
        User user=userService.getUserByToken(token);
        if (user == null) {
            model.addAttribute("message", "Invalid Token");
        }
        return "verification_form";
    }

    @PostMapping("/verification_form")
    public String verifyUser(@RequestParam(value="token") String token, @RequestParam(value = "password") String password, Model model){
        User user=userService.getUserByToken(token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "verification_form";
        }
        else {

            userService.updatePassword(user.getId(), password);
            userService.verifyUser(user.getId());
            userService.clearUserToken(user.getId());
            model.addAttribute("message", "Verified");
        }
        return "verification_form";
    }

    @GetMapping("/forgot_password")
    public String getForgetPasswordForm() {
        return "Forgot_Password_Form";
    }

    @PostMapping("/forgot_password")
    public String processPassword(@RequestParam(name="email") String email, Model model,HttpServletRequest request) {
        String serverUrl=getURL(request);
        String link="reset_password";
        String content="<p>Hello,</p>"
                + "<p>Click the link below to reset your password:</p>"
                + "<p><a href=\"%s\">Reset Password</a></p>";
        addAndEmailUser(email,request,content,link);

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
            userService.clearUserToken(user.getId());
            model.addAttribute("message", "Password Reset Successful");
        }
        return "Reset_Password_Form";
    }

    @GetMapping("/user_regestration_form")
    public String getRegestrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        List<String> listRoles = Arrays.asList(userConstants.USER_ROLE_MANAGER, userConstants.USER_ROLE_USER);
        model.addAttribute("listRoles", listRoles);

        return "user_regestration_form";
    }

    @PostMapping("/user_regestration_form")
    public String processForm(@ModelAttribute("user") User user,Model model,HttpServletRequest request) {
        userService.save(user);
        String link="verification_form";
        String content="<p>Hello,</p>"
                + "<p>Click the link below to verify your account:</p>"
                + "<p><a href=\"%s\">Verify Account</a></p>";
        addAndEmailUser(user.getEmail(),request,content,link);
        model.addAttribute("message", "User added successfully");
        return "user_regestration_form";
    }

}
