package com.joje.dbee.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joje.dbee.entity.account.UserRoleEntity;
import com.joje.dbee.entity.account.UserRoleKey;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleKey> {

}
