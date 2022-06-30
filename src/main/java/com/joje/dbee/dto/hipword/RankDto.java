package com.joje.dbee.dto.hipword;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankDto {

	private Long rankNo;
	private Integer songRank;
	private LocalDate regDate;
	private SongDto song;
	
}
