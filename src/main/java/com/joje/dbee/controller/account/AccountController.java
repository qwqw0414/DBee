package com.joje.dbee.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.service.account.UserService;

@Controller
public class AccountController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/login")
	public String viewLoginPage() throws Exception {
		return "account/login";
	}
	
	@GetMapping(value = "/signup")
	public String viewRegisterPage() throws Exception {
		return "account/register";
	}
	
	@PostMapping(value = "/signup")
	public String register(UserEntity user) throws Exception {
		userService.save(user);
		return "account/login";
	}
	
}
