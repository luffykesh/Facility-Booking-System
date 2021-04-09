package com.csci5308.g17;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@Controller
public class FacilityBookingSystemApplication{

    public static void main(String[] args){
        SpringApplication.run(FacilityBookingSystemApplication.class, args);
    }

    @GetMapping("/")
    public String login(){
        return "login";
    }
}
