package com.joje.dbee.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.repository.account.UserRepository;
import com.joje.dbee.service.AccountService;

@Service(value = "AccountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Optional<UserEntity> findByIdPw(String userId) {
		return Optional.of(userRepository.findByUserId(userId));
	}
	
}
