package com.csci5308.g17.user;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
    private IVerifyUser verifyUser;
    private List<String> emailList;
    private IGenerateServerURL url;



    public UserController(UserCSVParser csvService, EmailService emailService, VerifyUser varifyUser, List emailList, GenerateServerURL url) {
        userService = UserService.getInstance();
        this.csvService = csvService;
        this.emailService = emailService;
        this.verifyUser=varifyUser;
        this.emailList=emailList;
        this.url=url;


    }

    public void sendEmail(String email,HttpServletRequest request,String content,String link){
        String serverUrl=url.getURL(request);
        try {
            String token = userService.setUserToken(email);
            String resetPasswordLink = String.format("%s/"+link+"/%s", serverUrl, token);
            emailService.sendEmail(email, token, resetPasswordLink,content);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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
            String link="Varification_Form";
            String content="<p>Hello,</p>"
                    + "<p>Click the link below to verify your account:</p>"
                    + "<p><a href=\"%s\">Account Verification</a></p>";

            emailList=verifyUser.getUserList(userList);
            for(int i=0;i<emailList.size();i++) {
                sendEmail(emailList.get(i),request,content,link);
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
        String link="reset_password";
        String content="<p>Hello,</p>"
                + "<p>Click the link below to reset your password:</p>"
                + "<p><a href=\"%s\">Reset Passworf</a></p>";
        sendEmail(email,request,content,link);

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

    @GetMapping("/User_Regestration")
    public String getRegestrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        List<String> listRoles = Arrays.asList("Manager", "User");
        model.addAttribute("listRoles", listRoles);

        return "/User_Regestration";
    }

    @PostMapping("/User_Regestration")
    public String processForm(@ModelAttribute("user") User user,Model model,HttpServletRequest request) {
        userService.save(user);
        String link="Varification_Form";
        String content="<p>Hello,</p>"
                + "<p>Click the link below to verify your account:</p>"
                + "<p><a href=\"%s\">Varify Account</a></p>";
        sendEmail(user.getEmail(),request,content,link);
        model.addAttribute("message", "User added successfully");
        return "/User_Regestration";

    }

}
