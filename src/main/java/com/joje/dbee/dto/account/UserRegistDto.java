package com.joje.dbee.dto.account;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistDto {

	@NotBlank
	private String userId;
	
	@NotBlank
	private String userName;
	
	@NotBlank
	private String password;
	
}
