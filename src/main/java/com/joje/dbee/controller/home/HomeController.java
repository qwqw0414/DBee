package com.joje.dbee.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	@GetMapping(value = "/")
	public String viewHomePage() throws Exception {
		return "index";
	}
	
}
