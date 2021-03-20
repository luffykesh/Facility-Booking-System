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
    @GetMapping("/upload")

    public String csvpage(){
        return "/upload";

    }

    CSVread read;

    public CSVcontroller(CSVread read) {
        this.read = read;
       

    }
    @PostMapping("/upload")
    public String checkread(@RequestParam(name="file") MultipartFile file) throws IOException {
        if(CSVread.hasCSVFormat(file)) {
            ArrayList<User> l = (ArrayList) CSVread.readCSV(file.getInputStream());
            for (int i = 0; i < l.size(); i++) {
              System.out.printf(String.valueOf(l.get(i).getId()) +" "+ l.get(i).getName()+" "+ l.get(i).getEmail()+" "+ l.get(i).getPassword()+" "+ l.get(i).getRole(),l.get(i).getBannerId());

            }
            read.savetoDB(l);

        }
        else {
            System.out.println("No");
        }
        return "/upload";
    }



}



