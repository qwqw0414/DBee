package com.joje.dbee.dto.account;

import java.util.ArrayList;
import java.util.List;

import com.joje.dbee.common.contents.RoleType;
import com.joje.dbee.entity.account.RoleEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Long userNo;
	private String userId;
	private String userName;
	private String regDate;
	List<RoleType> roles;
	
	public void setRoles(List<RoleEntity> roles) {
		this.roles = new ArrayList<>();
		for (RoleEntity role : roles) {
			this.roles.add(role.getRoleName());
		}
	}
}
