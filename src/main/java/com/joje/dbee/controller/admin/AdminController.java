package com.joje.dbee.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/dbee/admin")
public class AdminController {

	
	@GetMapping(value = "")
	public String viewDashboardPage() throws Exception {
		return "admin/dashboard";
	}
	
	@GetMapping(value = "/account")
	public String viewAccountPage() throws Exception {
		return "admin/account";
	}
	
	@GetMapping(value = "/home")
	public String viewHomePage() throws Exception {
		return "admin/home";
	}
}
