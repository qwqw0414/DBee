package com.joje.dbee.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.service.account.UserService;

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
	@PostMapping(value = "/signup")
	public String register(UserEntity user) throws Exception {
		userService.save(user);
		return "account/login";
	}
}
