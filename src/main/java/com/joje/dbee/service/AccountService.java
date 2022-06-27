package com.joje.dbee.service;

import java.util.Optional;

import com.joje.dbee.entity.account.UserEntity;

public interface AccountService {

	Optional<UserEntity> findByIdPw(String userId);

}
