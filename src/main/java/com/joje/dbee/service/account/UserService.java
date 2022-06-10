package com.joje.dbee.service.account;

import com.joje.dbee.entity.account.UserEntity;

public interface UserService {

//	회원 가입
	public UserEntity save(UserEntity user) throws RuntimeException;
	
//	유저 정보 조회
	public UserEntity findUserById(String userId) throws RuntimeException;
	
//	유저 중복여부 체크
	public int userIdDuplicateCheck(String userId) throws RuntimeException;
	
}
