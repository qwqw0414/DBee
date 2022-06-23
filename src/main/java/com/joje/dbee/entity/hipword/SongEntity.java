package com.joje.dbee.entity.hipword;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity(name = "TB_HIPWORD_SONG")
public class SongEntity implements Serializable {

	private static final long serialVersionUID = -2408463812188243236L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long songNo;
	
	private String songId;
	private String songTitle;
	private String lyrics;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "artistNo")
	private ArtistEntity artist;
}
