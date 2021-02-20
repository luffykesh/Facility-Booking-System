package com.csci5408.g17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@RestController
public class FacilityBookingSystemApplication{

	public static void main(String[] args){
		SpringApplication.run(FacilityBookingSystemApplication.class, args);
	}

	@GetMapping
	public String hello(@RequestParam(value="name", required=false) String name){
		String greeting;
		if(name==null){
			greeting = "Hello World!";
		}
		else{
			greeting = String.format("Hello %s!", name);
		}
		return greeting;
	}
}
