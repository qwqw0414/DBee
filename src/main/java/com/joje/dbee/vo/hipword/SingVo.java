package com.joje.dbee.vo.hipword;

import java.io.Serializable;

import lombok.Data;

@Data
public class SingVo implements Serializable{
	
	private static final long serialVersionUID = 9051520495401102128L;

	private String id;
	private String title;
	private String lyrics;
	
}
