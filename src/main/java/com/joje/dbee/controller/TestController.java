package com.joje.dbee.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.joje.dbee.common.security.JwtTokenProvider;
import com.joje.dbee.vo.ResultVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/dbee/test")
public class TestController {

	private final Gson gson;
	private final JwtTokenProvider jwtTokenProvider;
	
	@GetMapping("")
	public ResponseEntity<?> roleTestAll(HttpServletRequest request) {
		ResultVo resultVo = new ResultVo();
		resultVo.put("userId", jwtTokenProvider.getUserId(request));
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER')")
	public ResponseEntity<?> roleTestUser(HttpServletRequest request) {
		ResultVo resultVo = new ResultVo();
		resultVo.put("userId", jwtTokenProvider.getUserId(request));
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> roleTestAdmin() {
		ResultVo resultVo = new ResultVo();
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
}
