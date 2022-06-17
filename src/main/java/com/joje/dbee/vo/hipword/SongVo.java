package com.joje.dbee.vo.hipword;

import java.io.Serializable;

import lombok.Data;

@Data
public class SongVo implements Serializable{
	
	private static final long serialVersionUID = 9051520495401102128L;

	private Integer singId;
	private String singTitle;
	private String lyrics;
	private String singer;
	private String album;
	
}
