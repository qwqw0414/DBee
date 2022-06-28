package com.joje.dbee.service;

import com.joje.dbee.dto.account.TokenResponseDto;
import com.joje.dbee.dto.account.UserDto;
import com.joje.dbee.dto.account.UserLoginDto;

public interface AuthService {

	TokenResponseDto login(UserLoginDto userLoginDto);

}
