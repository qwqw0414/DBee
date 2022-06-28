package com.joje.dbee.controller.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.joje.dbee.common.filter.JwtFilter;
import com.joje.dbee.common.utils.ParamUtil;
import com.joje.dbee.dto.account.TokenResponseDto;
import com.joje.dbee.dto.account.UserDto;
import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.service.AccountService;
import com.joje.dbee.service.AuthService;
import com.joje.dbee.service.UserService;
import com.joje.dbee.vo.ResultVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/dbee/account")
public class AccountRestController {

	private final UserService userService;
	
	private final AccountService accountService;
	
	private final AuthService authService;
	
	private Gson gson = new Gson();
	
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> authorize(String userId, String password) {

		UserDto userDto = new UserDto();
		userDto.setUserId(userId);
		userDto.setPassword(password);
		
		log.debug("[userDto]=[{}]", userDto);
		
		TokenResponseDto tokenResponseDto = authService.login(userDto);

		log.debug("[tokenResponseDto]=[{}]", tokenResponseDto);
		
        // 1. Response Header에 token 값을 넣어준다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenResponseDto.getToken());

        ResultVo resultVo = new ResultVo();
        
        // 2. Response Body에 token 값을 넣어준다.
        return new ResponseEntity<>(gson.toJson(resultVo), httpHeaders, HttpStatus.OK);
    }
	
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
	
//	@GetMapping("users/info")
//	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
//	public ResponseEntity<? extends BasicResponse> getMyInfo() {
//		return ResponseEntity.ok(new CommonResponse<>(userService.findUserInfo()));
//	}
}
