package com.joje.dbee.entity.hipword;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "TB_HIPWORD_SONG")
@EqualsAndHashCode
public class SongEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long songNo;
	
	private String songId;
	private String songTitle;
	private String lyrics;
	
	@OneToOne
	@JoinColumn(name = "ARTIST_NO")
	private ArtistEntity artist;
	
}
