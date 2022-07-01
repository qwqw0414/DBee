package com.joje.dbee.service;

import java.time.LocalDate;
import java.util.List;

import org.jsoup.nodes.Document;

import com.joje.dbee.dto.hipword.RankDto;
import com.joje.dbee.dto.hipword.SongDto;
import com.joje.dbee.entity.hipword.ArtistEntity;
import com.joje.dbee.entity.hipword.SongEntity;
import com.joje.dbee.entity.hipword.RankEntity;

public interface HipwordService {

	List<RankDto> getChartListToMelon();

	SongDto getSongById(String songId);

	int addRank(List<RankDto> ranks);

	String getSongIdToMelon(String keyword);

	LocalDate getRecentRankDate();

	List<String> getLyricsList();
}
