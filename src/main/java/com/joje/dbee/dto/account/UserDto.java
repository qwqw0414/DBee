package com.joje.dbee.dto.account;

import java.util.List;

import com.joje.dbee.common.contents.RoleType;

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
	private String password;
	private String regDate;
	List<RoleType> roles;
}
