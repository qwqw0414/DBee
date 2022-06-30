package com.joje.dbee.service;

import java.util.List;

import com.joje.dbee.dto.account.UserDto;
import com.joje.dbee.dto.admin.RequestSearchUserDto;

public interface AdminService {

	List<UserDto> searchUser(RequestSearchUserDto requestSearchUserDto);

}
