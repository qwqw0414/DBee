package com.joje.dbee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.joje.dbee.vo.ResultVo;

@RestController
@RequestMapping(value = "/dbee/user")
public class TestController {

	private final Gson gson = new Gson();
	
	@GetMapping("")
	public ResponseEntity<?> roleTestAll() {
		ResultVo resultVo = new ResultVo();
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER')")
	public ResponseEntity<?> roleTestUser() {
		ResultVo resultVo = new ResultVo();
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> roleTestAdmin() {
		ResultVo resultVo = new ResultVo();
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
}
