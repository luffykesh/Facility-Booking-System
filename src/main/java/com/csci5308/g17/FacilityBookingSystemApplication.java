package com.csci5308.g17;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;


@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@Controller
public class FacilityBookingSystemApplication{
	private JdbcTemplate template;

	public FacilityBookingSystemApplication(JdbcTemplate template){
		this.template = template;

	}
	public static void main(String[] args){

		SpringApplication.run(FacilityBookingSystemApplication.class, args);
	}
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		this.template.execute("show tables");
	}

}
