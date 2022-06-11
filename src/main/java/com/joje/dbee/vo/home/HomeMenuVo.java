package com.joje.dbee.vo.home;

import java.io.Serializable;

import lombok.Data;

@Data
public class HomeMenuVo implements Serializable {
	
	private static final long serialVersionUID = -2510512381643973161L;
	
	private Long menuNo;
	private String title;
	private String url;

}
