package com.joje.dbee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joje.dbee.dto.account.UserDto;
import com.joje.dbee.dto.admin.RequestSearchUserDto;
import com.joje.dbee.service.AdminService;
import com.joje.dbee.vo.ResultVo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/dbee/admin", produces = "application/json; charset=utf8")
public class AdminController {

	private final AdminService adminService;
	
	@GetMapping(value = "/user")
	public ResponseEntity<ResultVo> searchUser(@RequestBody RequestSearchUserDto requestSearchUserDto) throws Exception {

		List<UserDto> users = adminService.searchUser(requestSearchUserDto);
		
		ResultVo resultVo = new ResultVo();
		resultVo.put("users", users);

		return new ResponseEntity<>(resultVo, HttpStatus.OK);
	}
	
	
}
