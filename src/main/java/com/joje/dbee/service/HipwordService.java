package com.joje.dbee.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.joje.dbee.vo.hipword.ArtistVo;
import com.joje.dbee.vo.hipword.SongRankVo;
import com.joje.dbee.vo.hipword.SongVo;

public interface HipwordService {

	List<SongRankVo> getChartListToMelon();

	List<String> getLyricsToMelon(Document doc);

	String getSongIdToMelon(Document doc);

	String getSongTitleByMelon(Document doc);

	ArtistVo getArtistByMelon(Document doc);

	void insertSong(String songId);

	SongVo selectOneSongById(String songId);

	int insertSongRank(List<SongRankVo> songs);

	List<SongRankVo> selectAllSongRankByDate(String now);

}
