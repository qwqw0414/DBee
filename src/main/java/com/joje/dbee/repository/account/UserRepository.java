package com.joje.dbee.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joje.dbee.entity.account.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public UserEntity findByUserId(String userId) throws RuntimeException;
	public int countByUserId(String userId) throws RuntimeException;
}
