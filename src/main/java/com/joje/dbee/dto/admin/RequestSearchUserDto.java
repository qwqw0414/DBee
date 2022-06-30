package com.joje.dbee.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSearchUserDto {

	private String keyword = "";
	private Integer page = 1;
	
}
