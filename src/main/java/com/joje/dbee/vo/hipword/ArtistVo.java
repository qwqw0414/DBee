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
public class ArtistVo implements Serializable {

	private static final long serialVersionUID = -2582064199334849513L;

	private String artistId;
	private String artistName;

}
