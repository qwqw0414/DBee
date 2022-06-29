package com.joje.dbee.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.joje.dbee.dto.hipword.SongDto;
import com.joje.dbee.entity.hipword.ArtistEntity;
import com.joje.dbee.entity.hipword.SongEntity;
import com.joje.dbee.entity.hipword.RankEntity;

public interface HipwordService {

	List<RankEntity> getChartListToMelon();

	List<String> getLyricsToMelon(Document doc);

	String getSongIdToMelon(String keyword);

	String getSongTitleByMelon(Document doc);

	ArtistEntity getArtistByMelon(Document doc);

	SongDto getSongById(String songId);

	int addRank(List<RankEntity> ranks);
}
