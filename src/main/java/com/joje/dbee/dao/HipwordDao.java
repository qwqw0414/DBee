package com.joje.dbee.dao;

import java.util.List;
import java.util.Map;

import com.joje.dbee.vo.hipword.ArtistVo;
import com.joje.dbee.vo.hipword.SongRankVo;
import com.joje.dbee.vo.hipword.SongVo;

public interface HipwordDao {

	int countSongById(String songId);

	int countArtistById(String artistId);

	int insertArtist(ArtistVo artist);

	int insertSong(Map<String, Object> song);

	SongVo selectOneSongById(String songId);

	int insertAllSongRank(List<SongRankVo> songs);

	List<SongRankVo> selectAllSongRankByDate(String now);

}
