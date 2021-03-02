package com.csci5308.g17;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Controller;


@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@Controller
public class FacilityBookingSystemApplication{

	public static void main(String[] args){
		SpringApplication.run(FacilityBookingSystemApplication.class, args);
	}

}
