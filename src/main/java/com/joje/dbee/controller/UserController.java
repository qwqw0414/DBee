package com.joje.dbee.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joje.dbee.common.contents.StatusCode;
import com.joje.dbee.common.security.JwtTokenProvider;
import com.joje.dbee.dto.account.UserDto;
import com.joje.dbee.exception.DBeeException;
import com.joje.dbee.service.AccountService;
import com.joje.dbee.vo.ResultVo;

import lombok.RequiredArgsConstructor;

/**
 * 사용자 서비스 관련 컨트롤러
 *
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/dbee/user")
public class UserController {

	private final AccountService accountService;
	private final JwtTokenProvider jwtTokenProvider;
	
	@GetMapping(value = "/myinfo")
	public ResponseEntity<ResultVo> myinfo(HttpServletRequest request) throws Exception {
		
		UserDto user = accountService.findByUserNo(jwtTokenProvider.getUserNo(request));
		
		if(user == null)
			throw new DBeeException(StatusCode.FAILED_NO_DATA, "유저 정보가 없습니다.");
		
		ResultVo resultVo = new ResultVo();
		resultVo.put("user", user);
		
		return new ResponseEntity<>(resultVo, HttpStatus.OK);
	}
	
}
