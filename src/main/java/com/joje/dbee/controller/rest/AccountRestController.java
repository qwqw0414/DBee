package com.joje.dbee.controller.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.joje.dbee.common.contents.StatusCode;
import com.joje.dbee.common.utils.ParamUtil;
import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.service.UserService;
import com.joje.dbee.vo.ResultVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/dbee/account")
public class AccountRestController {

	@Autowired
	private UserService userService;
	
	private Gson gson = new Gson();
	
	/**
	 * 아이디 중복 체크
	 */
	@GetMapping(value = "/duple/{userId}")
	public ResponseEntity<?> idDuplicateCheck(@PathVariable(value = "userId") String userId) throws Exception {
		
		int countId = userService.userIdDuplicateCheck(userId);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setStatus(StatusCode.SUCCESS);
		resultVo.put("countId", countId);
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
	/**
	 * 회원 가입
	 * 
	 */
	@PostMapping(value = "/signup")
	public ResponseEntity<?> register(@RequestBody Map<String, Object> body) throws Exception {

		UserEntity user = new UserEntity();
		user.setUserId(ParamUtil.toStr(body.get("userId")));
		user.setUserName(ParamUtil.toStr(body.get("userName")));
		user.setPassword(ParamUtil.toStr(body.get("password")));

		log.debug("[user]=[{}]", user);
		
		userService.save(user);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setStatus(StatusCode.SUCCESS);
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
}
