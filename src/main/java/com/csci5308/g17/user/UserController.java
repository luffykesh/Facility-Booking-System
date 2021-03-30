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
    private IVerifyUser verifyUser;
    private List<String> emailList;
    private IGenerateServerURL url;
    private IEmailService verifiactionEmail;

    public UserController(UserCSVParser csvService, EmailService emailService, VerifyUser varifyUser, List emailList, GenerateServerURL url) {
        userService = UserService.getInstance();
        this.csvService = csvService;
        this.emailService = emailService;
        this.verifyUser=varifyUser;
        this.emailList=emailList;
        this.url=url;

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
            String content="<p>Hello,</p>"
                    + "<p>Click the link below to verify your account:</p>"
                    + "<p><a href=\"%s\">Account Verification</a></p>";
            String serverUrl=url.getURL(request);
            emailList=verifyUser.getUserList(userList);
            for(int i=0;i<emailList.size();i++) {
                try{
                    String token = userService.setUserToken(emailList.get(i));
                    String verifyAccountLink = String.format("%s/Varification_Form/%s", serverUrl, token);
                    emailService.sendEmail(emailList.get(i),token,verifyAccountLink,content);
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                }
                catch (MessagingException e) {
                    e.printStackTrace();
                }

            }

        }
        return "redirect:/admin/home";
    }

    @GetMapping("/Varification_Form/{token}")
    public String getVerificationForm(@PathVariable("token") String token, Model model) {
        User user=userService.getUserByToken(token);
        if (user == null) {
            model.addAttribute("message", "Invalid Token");
        }
        return "Varification_Form";
    }
    @PostMapping("/Varification_Form")
    public String verifyUser(@RequestParam(value="token") String token, @RequestParam(value = "password") String password, Model model){
        User user=userService.getUserByToken(token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "Varification_Form";
        }
        else {
            int verified=1;
            userService.updatePassword(user.getId(), password);
            userService.setVerification(user.getId(),verified);
            userService.clearUserToken(user.getId());
            model.addAttribute("message", "Verified");
        }
        return "Varification_Form";

    }

    @GetMapping("/forgot_password")
    public String getForgetPasswordForm() {
        return "Forgot_Password_Form";
    }



    @PostMapping("/forgot_password")
    public String processPassword(@RequestParam(name="email") String email, Model model,HttpServletRequest request) {
        String serverUrl=url.getURL(request);
        String resetPasswordLink;
        String content = "<p>Hello,</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"%s\">Change password</a></p>";
        try {
            String token = userService.setUserToken(email);
            resetPasswordLink = String.format("%s/reset_password/%s", serverUrl, token);
            emailService.sendEmail(email, token, resetPasswordLink,content);
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
            userService.clearUserToken(user.getId());
            model.addAttribute("message", "Password Reset Successful");
        }
        return "Reset_Password_Form";
    }

}
