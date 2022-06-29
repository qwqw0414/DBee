package com.joje.dbee.service;


import com.joje.dbee.dto.account.UserDto;
import com.joje.dbee.dto.account.UserRegistDto;

public interface AccountService {

	UserDto signup(UserRegistDto userRegistDto) throws RuntimeException;

	UserDto findByUserId(String userId);

	int userIdDuplicateCheck(String userId);

	UserDto findUserInfo();

	UserDto findByUserNo(long userNo);
	
}
