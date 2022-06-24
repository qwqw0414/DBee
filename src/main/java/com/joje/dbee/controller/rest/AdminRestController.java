package com.joje.dbee.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.joje.dbee.common.contents.StatusCode;
import com.joje.dbee.common.utils.ParamUtil;
import com.joje.dbee.dto.account.RoleDto;
import com.joje.dbee.dto.account.UserDto;
import com.joje.dbee.service.AdminService;
import com.joje.dbee.vo.ResultVo;
import com.joje.dbee.vo.common.PaginationVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/dbee/admin")
public class AdminRestController {
	
	@Autowired
	private AdminService adminService;
	
	private Gson gson = new Gson();
	

	/**
	 * 사용자 정보 조회
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/account/{userNo}", produces = "application/json; charset=utf8")
	public ResponseEntity<?> getUserInfo(@PathVariable(value = "userNo") long userNo) throws Exception {
		
		UserDto user = adminService.getUserInfo(userNo);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setStatus(StatusCode.SUCCESS);
		resultVo.put("user", user);
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
	/**
	 * 사용자 정보 업데이트
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/account/update", produces = "application/json; charset=utf8")
	public ResponseEntity<?> userDetailUpdate(@RequestBody Map<String, Object> body) throws Exception {
		log.debug("[body]=[{}]", body);
		
//		파라미터 Set
		UserDto userDto = new UserDto();
		userDto.setUserNo(ParamUtil.toLong(body, "userNo"));
		userDto.setUserId(ParamUtil.toStr(body, "userId"));
		userDto.setUserName(ParamUtil.toStr(body, "userName"));
		
		List<Object> roleIdList = (List<Object>) ParamUtil.toList(body, "roles");
		List<RoleDto> roles = new ArrayList<>();
		for(int i = 0; i < roleIdList.size(); i++) {
			String obj = String.valueOf(roleIdList.get(i)).replace(".0", "");
			log.debug(obj);
			roles.add(new RoleDto(ParamUtil.toLong(obj), null));
		}
			
		userDto.setRoles(roles);
		
		log.debug("[param]=[{}]", userDto);

//		사용자 정보 업데이트(결과처리 추가 필요)
		adminService.updateUserDetail(userDto);
		
//		Result Set
		ResultVo resultVo = new ResultVo();
		resultVo.setStatus(StatusCode.SUCCESS);

		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}

	/**
	 * 사용자 검색
	 * @param keyword
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/account/list", produces = "application/json; charset=utf8")
	public ResponseEntity<?> searchUser(@RequestParam(value = "keyword", defaultValue = "") String keyword,
										@RequestParam(value = "page", defaultValue = "1") int page) throws Exception {
		
		log.debug("[keyword]=[{}]", keyword);
		
//		파라미터 셋
		Map<String, Object> param = new HashMap<>();
		param.put("keyword", keyword);
		
//		페이징 처리를 위한 데이터 설정
		int totalContents = adminService.countUserByKeyword(param);
		PaginationVo pagination = new PaginationVo(page, totalContents);
		param.put("paination", pagination);
		
		List<Map<String, Object>> users = adminService.searchUserByKeyword(param);
		
//		결과폼 셋
		ResultVo resultVo = new ResultVo();
		resultVo.put("users", users);
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
}
