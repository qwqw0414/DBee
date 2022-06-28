package com.joje.dbee.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joje.dbee.common.contents.RoleType;
import com.joje.dbee.entity.account.RoleEntity;
import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.repository.account.RoleRepository;
import com.joje.dbee.repository.account.UserRepository;
import com.joje.dbee.service.UserService;

@Service(value = "UserService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	/**
	 * 회원가입
	 */
	@Override
	@Transactional
	public UserEntity save(UserEntity user) throws RuntimeException {
//		비밀번호 암호화
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
//		계정 활성화
		user.setEnabled(true);
		
//		롤 세팅
		RoleEntity role = new RoleEntity();
		
		role.setRoleId(RoleType.ROLE_USER.getRoleId());
		user.getRoles().add(role);
		
		return userRepository.save(user);
	}

	/**
	 * 유저 정보 조회
	 */
	@Override
	@Transactional
	public UserEntity findUserById(String userId) throws RuntimeException {
		return userRepository.findByUserId(userId).get();
	}

	/**
	 * 유저 중복 체크
	 */
	@Override
	@Transactional
	public int userIdDuplicateCheck(String userId) throws RuntimeException {
		return userRepository.countByUserId(userId);
	}

}
