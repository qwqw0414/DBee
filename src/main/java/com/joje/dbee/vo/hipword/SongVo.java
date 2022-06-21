package com.joje.dbee.vo.hipword;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SongVo implements Serializable {

	private static final long serialVersionUID = 9051520495401102128L;

	private String songId;
	private String songTitle;
	private String artist;
	private String lyrics;
	
}
