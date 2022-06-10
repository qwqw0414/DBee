package com.joje.dbee.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joje.dbee.entity.account.RoleEntity;
import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.repository.RoleRepository;
import com.joje.dbee.repository.UserRepository;
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
		List<RoleEntity> roles = roleRepository.findByRoleName("ROLE_USER");
		
		role.setRoleId(roles.get(0).getRoleId());
		user.getRoles().add(role);
		
		return userRepository.save(user);
	}

	/**
	 * 유저 정보 조회
	 */
	@Override
	@Transactional
	public UserEntity findUserById(String userId) throws RuntimeException {
		return userRepository.findByUserId(userId);
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
