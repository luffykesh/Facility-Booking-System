package com.csci5308.g17.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Controller
public class UserController {

    IUserService userService;
    private ICSVservice csvService;

    public UserController(CSVservice csvService) {
        userService = UserService.getInstance();
        this.csvService = csvService;
    }

    @GetMapping("/admin/user/upload")
    public String userCSVUploadForm(){
        return "upload";
    }

    @PostMapping("/admin/user/upload")
    public String userCSVData(@RequestParam(name="file") MultipartFile file) throws IOException {
        if(csvService.hasCSVFormat(file)) {
            List<User> userList = csvService.readCSV(file.getInputStream());
            userService.savetoDB(userList);
        }
        return "redirect:/admin/home";
    }
}
