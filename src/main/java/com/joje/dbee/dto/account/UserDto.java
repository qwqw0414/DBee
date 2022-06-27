package com.joje.dbee.dto.account;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Long userNo;
	private String userId;
	private String userName;
	private String regDate;
	List<RoleDto> roles;
}