package com.joje.dbee.common.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.joje.dbee.entity.account.UserEntity;

/**
 * Spring Security가 /login.do 요청이 들어오면
 * 로그인이 완료되면 Security Session 을 생성한다. ( SecurityHolder )
 * Object Type => Authentication 타입 객체
 * Authentication 안에 User 정보가 있어야 함.
 * User Object Type => UserDetails Type 객체
 * 
 * Security Session -> Authentication -> UserDetails(PrincipalDetails)
 */
@SuppressWarnings("serial")
public class PrincipalDetails implements UserDetails {

	private UserEntity user;

    public PrincipalDetails(UserEntity user){
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authList = new ArrayList<>();
//        authList.add(new SimpleGrantedAuthority());
        return authList;
    }
	
}
