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

    CSVservice read;

    public CSVcontroller(CSVservice read) {
        this.read = read;
       

    }
    @PostMapping("/upload")
    public String checkread(@RequestParam(name="file") MultipartFile file) throws IOException {
        if(CSVservice.hasCSVFormat(file)) {
            ArrayList<User> l = (ArrayList) CSVservice.readCSV(file.getInputStream());
            read.savetoDB(l);

        }
        return "/upload";
    }



}



