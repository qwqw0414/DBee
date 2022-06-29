package com.joje.dbee.entity.hipword;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "TB_HIPWORD_ARTIST")
public class ArtistEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long artistNo;
	
	private String artistId;
	private String artistName;
}
