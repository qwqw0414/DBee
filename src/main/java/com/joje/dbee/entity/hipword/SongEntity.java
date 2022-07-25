package com.joje.dbee.entity.hipword;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_HIPWORD_SONG")
public class SongEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long songNo;
	
	private String songId;
	private String songTitle;
	private String lyrics;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "artistNo")
	private ArtistEntity artist;
}
