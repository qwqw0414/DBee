package com.joje.dbee.entity.hipword;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "TB_HIPWORD_RANK")
@EqualsAndHashCode
public class RankEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rankNo;
	
	@Column
	private Long songNo;
	private Integer songRank;
	private LocalDate regDate = LocalDate.now();
	
//	@OneToOne
//	@JoinColumn(name = "SONG_NO")
//	private SongEntity song;
	
	
	
}
