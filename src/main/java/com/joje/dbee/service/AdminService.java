package com.joje.dbee.service;

import java.util.List;
import java.util.Map;

import com.joje.dbee.dto.account.UserDto;

public interface AdminService {
	
	int countUserByKeyword(Map<String, Object> param) throws RuntimeException;

	List<Map<String, Object>> searchUserByKeyword(Map<String, Object> param) throws RuntimeException;

	UserDto getUserInfo(long userNo) throws RuntimeException;

	void updateUserDetail(UserDto userDto) throws RuntimeException;
	
}
