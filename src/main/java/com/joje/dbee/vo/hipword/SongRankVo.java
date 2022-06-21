package com.joje.dbee.vo.hipword;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SongRankVo extends SongVo{
	
	private static final long serialVersionUID = -789049053011174674L;
	
	private int rankNo;
	private int songRank;
	private LocalDate regDate;
	
	public SongRankVo() {
		super();
	}
	
	
}
