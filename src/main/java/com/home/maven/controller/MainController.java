package com.home.maven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/index")
	public String mainPage() throws Exception{
		return "index";
	}

}
