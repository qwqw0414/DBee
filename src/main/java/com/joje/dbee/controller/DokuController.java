package com.joje.dbee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/dbee/doku")
public class DokuController {

	
	@GetMapping(value = "")
	public String viewDokuMainPage() throws Exception {
		return "doku/doku-main";
	}
		
	
}
