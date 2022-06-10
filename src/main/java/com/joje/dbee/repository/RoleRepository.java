package com.joje.dbee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joje.dbee.entity.account.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	public List<RoleEntity> findByRoleName(String roleName) throws RuntimeException;
	
}
