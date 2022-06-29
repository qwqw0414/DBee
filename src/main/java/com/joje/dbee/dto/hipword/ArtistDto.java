package com.joje.dbee.dto.hipword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto {

	private Long artistNo;
	private String artistId;
	private String artistName;
	
}
