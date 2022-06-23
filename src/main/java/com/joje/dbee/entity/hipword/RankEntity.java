package com.joje.dbee.entity.hipword;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity(name = "TB_HIPWORD_RANK")
public class RankEntity implements Serializable {

	private static final long serialVersionUID = 6037139687498666914L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rankNo;
	
	private Integer songRank;
	private LocalDate regDate = LocalDate.now();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "songNo")
	private SongEntity song;
	
	
	
}
