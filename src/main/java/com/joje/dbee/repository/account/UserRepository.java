package com.joje.dbee.repository.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joje.dbee.entity.account.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public Optional<UserEntity> findByUserId(String userId);
	public int countByUserId(String userId);
	public Optional<UserEntity> findByUserNo(long userId);
}
