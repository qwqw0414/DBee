package com.joje.dbee.service;

import com.joje.dbee.dto.account.TokenResponseDto;
import com.joje.dbee.dto.account.UserDto;

public interface AuthService {

	TokenResponseDto login(UserDto loginDto);

}
