package com.joje.dbee.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joje.dbee.dto.account.UserDto;
import com.joje.dbee.dto.admin.RequestSearchUserDto;
import com.joje.dbee.repository.account.UserRepository;
import com.joje.dbee.repository.account.UserRoleRepository;
import com.joje.dbee.service.AdminService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service(value = "AdminService")
public class AdminServiceImpl implements AdminService {
	
	private final UserRepository repository;
	private final UserRoleRepository userRoleRepository;
	
	@Override
	public List<UserDto> searchUser(RequestSearchUserDto requestSearchUserDto) {
		return null;
	}

}
