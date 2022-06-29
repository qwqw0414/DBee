package com.joje.dbee.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joje.dbee.common.contents.RoleType;
import com.joje.dbee.common.contents.StatusCode;
import com.joje.dbee.common.utils.SecurityUtil;
import com.joje.dbee.dto.account.TokenResponseDto;
import com.joje.dbee.dto.account.UserDto;
import com.joje.dbee.dto.account.UserRegistDto;
import com.joje.dbee.entity.account.RoleEntity;
import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.exception.DBeeException;
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
	private final ModelMapper modelMapper;
	
	
	/**
	 * 사용자 회원 가입
	 */
    @Transactional
    @Override
    public UserDto signup(UserRegistDto userRegistDto) throws RuntimeException {
        if (userRepository.countByUserId(userRegistDto.getUserId()) > 0) {
            throw new DBeeException(StatusCode.DUPLICATE_DATA, "이미 가입된 유저입니다.");
        }

//      초기 권한
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(new RoleEntity(RoleType.ROLE_USER));
        
        UserEntity user = UserEntity.builder()
                					.userId(userRegistDto.getUserId())
                					.password(passwordEncoder.encode(userRegistDto.getPassword()))
                					.userName(userRegistDto.getUserName())
                					.regDate(LocalDateTime.now())
                					.roles(roles)
                					.build();

//      사용자 정보 저장
        user = userRepository.save(user);
        
        UserDto userDto = new UserDto();
        modelMapper.map(user, userDto);
        
        return userDto;
    }
	
    @Override
    public UserDto findByUserId(String userId) {
    	
    	UserEntity user = userRepository.findByUserId(userId).get();
    	log.debug("[user]=[{}]", user);
    	
    	UserDto userDto = new UserDto();
    	modelMapper.map(user, userDto);
    	
    	return userDto;
    }

    @Override
	public UserDto findUserInfo() {
		String userId = SecurityUtil.getCurrentId()
				.orElseThrow(() -> new RuntimeException("Security Context에 인증 정보가 없습니다."));

    	UserDto userDto = new UserDto();
    	modelMapper.map(userRepository.findByUserId(userId), userDto);
		
    	return userDto;
	}

	@Override
	public int userIdDuplicateCheck(String userId) {
		return userRepository.countByUserId(userId);
	}
}
