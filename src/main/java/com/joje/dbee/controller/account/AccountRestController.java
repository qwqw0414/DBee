package com.joje.dbee.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.joje.dbee.common.contents.StatusCode;
import com.joje.dbee.service.account.UserService;
import com.joje.dbee.vo.common.ResultVo;

@RestController
@RequestMapping(value = "/account")
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
	
}
