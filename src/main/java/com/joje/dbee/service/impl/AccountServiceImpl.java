package com.joje.dbee.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joje.dbee.common.contents.RoleType;
import com.joje.dbee.common.utils.SecurityUtil;
import com.joje.dbee.component.ModelMapperComponent;
import com.joje.dbee.dto.account.TokenResponseDto;
import com.joje.dbee.dto.account.UserDto;
import com.joje.dbee.entity.account.RoleEntity;
import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.repository.account.UserRepository;
import com.joje.dbee.service.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service(value = "AccountService")
public class AccountServiceImpl implements AccountService {

	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final ModelMapperComponent mapper;
	
	
    @Transactional
    @Override
    public UserDto signup(UserDto userDto) {
        if (userRepository.findByUserId(userDto.getUserId()) != null) {
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(new RoleEntity(RoleType.ROLE_USER));
        
        UserEntity user = UserEntity.builder()
                					.userId(userDto.getUserId())
                					.password(passwordEncoder.encode(userDto.getPassword()))
                					.userName(userDto.getUserName())
                					.roles(roles)
                					.build();

        userRepository.save(user);
        
        return userDto;
    }
	
    @Override
    public UserDto findByUserId(String userId) {
    	
    	UserEntity user = userRepository.findByUserId(userId).get();
    	log.debug("[user]=[{}]", user);
    	
    	UserDto userDto = new UserDto();
    	mapper.modelMapper().map(user, userDto);
    	
    	return userDto;
    }
	
    
	public UserDto findUserInfo() {
		String userId = SecurityUtil.getCurrentId()
				.orElseThrow(() -> new RuntimeException("Security Context에 인증 정보가 없습니다."));

    	UserDto userDto = new UserDto();
    	mapper.modelMapper().map(userRepository.findByUserId(userId), userDto);
		
    	return userDto;
	}

	@Override
	public TokenResponseDto login(UserDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
