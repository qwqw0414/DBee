package com.joje.dbee.service;


import com.joje.dbee.dto.account.TokenResponseDto;
import com.joje.dbee.dto.account.UserDto;

public interface AccountService {

	UserDto signup(UserDto userDto);

	UserDto findByUserId(String userId);

	TokenResponseDto login(UserDto userDto);

	
}
