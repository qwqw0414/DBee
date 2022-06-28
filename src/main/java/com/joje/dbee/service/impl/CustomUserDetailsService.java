package com.joje.dbee.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joje.dbee.entity.account.RoleEntity;
import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.repository.account.UserRepository;

import lombok.RequiredArgsConstructor;


/**
 * UserDetailsService 인터페이스를 구현한 클래스이다. 
 * 위에서 언급한 authticate메소드가 실행될 때
 * loadUserByUsername을 통하여 DB정보를 가져온 후 사용자의 PW가 일치하는지 검증한다.
 * UserDetails 객체를 반환하기 위해 createUserDetails 메소드에서
 * User entity의 정보를 기반으로 org.springframework.security.core.userdetails.User를 생성하여 반환한다.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUserId(username)
                			 .map(user -> createUserDetails(user))
                			 .orElseThrow(() -> new UsernameNotFoundException(username + " 존재하지 않는 UserId 입니다."));
	}

    private UserDetails createUserDetails(UserEntity user) {
//        GrantedAuthority grantedAuthority =  new SimpleGrantedAuthority(user.getRoles().toString());
    	List<String> roles = new ArrayList<>();
    	for(RoleEntity role : user.getRoles()) {
    		roles.add(role.getRoleName().toString());
    	}
    	
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(String.join(",", roles));
        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
	
}
