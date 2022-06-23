package com.joje.dbee.dto.account;

import com.joje.dbee.common.contents.RoleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
	
	private Long roleId;
	private RoleType roleName;
	
}
