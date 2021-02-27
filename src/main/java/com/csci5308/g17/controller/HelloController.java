package com.csci5308.g17.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @GetMapping
	public String hello(@RequestParam(value="name", required=false) String name, Model model){

		String greeting;
		if(name==null){
			greeting = "Hello World!";
		}
		else{
			greeting = String.format("Hello %s!", name);
		}
		model.addAttribute("name", greeting);
		return "helloworld";
	}
}
