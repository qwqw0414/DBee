package com.joje.dbee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/dbee/hipword")
public class HipwordController {

	
	@GetMapping(value = "")
	public String viewHipwordMainPage() throws Exception {
		return "hipword/hipword-main";
	}
	
}
