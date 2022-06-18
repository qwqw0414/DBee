package com.joje.dbee.vo.hipword;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AlbumVo implements Serializable {

	private static final long serialVersionUID = -4883545446221284741L;
	
	private Integer albumNo;
	private String albumId;
	private String albumName;
	
}
