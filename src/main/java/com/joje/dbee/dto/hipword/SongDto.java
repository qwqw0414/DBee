package com.joje.dbee.dto.hipword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {

	private Long songNo;
	private String songId;
	private String songTitle;
	private String lyrics;
	private ArtistDto artist;
	
}
