package com.csci5308.g17.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class CSVcontroller {
    private CSVservice readFile;
    private UserService userService;

    public CSVcontroller(CSVservice read,UserService userService) {
        this.readFile = read;
        this.userService=userService;
    }

    @GetMapping("/upload")
    public String getCSV(){
        return "/upload";
    }

    @PostMapping("/upload")
    public String uploadCSV(@RequestParam(name="file") MultipartFile file) throws IOException {
        if(readFile.hasCSVFormat(file)) {
            ArrayList<User> userList = (ArrayList) readFile.readCSV(file.getInputStream());
            userService.savetoDB(userList);
        }
        else {
            throw new IOException("Format is incorrect");
        }
        return "/upload";
    }
}
