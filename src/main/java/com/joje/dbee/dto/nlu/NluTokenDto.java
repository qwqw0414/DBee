package com.joje.dbee.dto.nlu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NluTokenDto {
	
	private String pos;
	private String text;
	
}
