package com.joje.dbee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.service.UserService;

@Controller
@RequestMapping(value = "/dbee/account")
public class AccountController {

	@Autowired
	private UserService userService;
	
	/**
	 * 로그인 페이지 이동
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/login")
	public String viewLoginPage() throws Exception {
		return "account/login";
	}
	
	/**
	 * 회원가입 페이지 이동
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/signup")
	public String viewRegisterPage() throws Exception {
		return "account/register";
	}
	
	/**
	 * 회원가입 처리
	 * @param user
	 * @return
	 * @throws Exception
	 */
//	@PostMapping(value = "/signup")
//	public String register(@RequestParam String userId, 
//						   @RequestParam String userName, 
//						   @RequestParam String password) throws Exception {
//
//		UserEntity user = new UserEntity();
//		user.setUserId(userId);
//		user.setUserName(userName);
//		user.setPassword(password);
//		
//		userService.save(user);
//		return "account/login";
//	}
}
