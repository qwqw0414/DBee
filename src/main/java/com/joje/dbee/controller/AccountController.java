package com.joje.dbee.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.joje.dbee.common.filter.JwtFilter;
import com.joje.dbee.dto.account.TokenResponseDto;
import com.joje.dbee.dto.account.UserDto;
import com.joje.dbee.dto.account.UserLoginDto;
import com.joje.dbee.dto.account.UserRegistDto;
import com.joje.dbee.service.AccountService;
import com.joje.dbee.service.AuthService;
import com.joje.dbee.vo.ResultVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/dbee/account")
public class AccountController {

	private final AccountService accountService;
	private final AuthService authService;
	private final Gson gson;
	
	/**
	 * 로그인
	 */
	@PostMapping(value = "/login", produces = "application/json; charset=utf8")
	public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) throws Exception {

		TokenResponseDto tokenResponseDto = authService.login(userLoginDto);
		
        // 1. Response Header에 token 값을 넣어준다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenResponseDto.getToken());

        ResultVo resultVo = new ResultVo();
        
        return new ResponseEntity<>(gson.toJson(resultVo), httpHeaders, HttpStatus.OK);
    }
	
	/**
	 * 아이디 중복 체크
	 */
	@GetMapping(value = "/duple/{userId}", produces = "application/json; charset=utf8")
	public ResponseEntity<?> idDuplicateCheck(@PathVariable(value = "userId") String userId) throws Exception {
		
		int countId = accountService.userIdDuplicateCheck(userId);
		
		ResultVo resultVo = new ResultVo();
		resultVo.put("countId", countId);
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
	/**
	 * 회원 가입
	 * 
	 */
	@PostMapping(value = "/signup", produces = "application/json; charset=utf8")
	public ResponseEntity<ResultVo> register(@RequestBody UserRegistDto userRegistDto) throws Exception {
		
		UserDto userDto = accountService.signup(userRegistDto);
		
		ResultVo resultVo = new ResultVo();
		resultVo.put("user", userDto);
		
		return new ResponseEntity<>(resultVo, HttpStatus.OK);
	}
	
}
