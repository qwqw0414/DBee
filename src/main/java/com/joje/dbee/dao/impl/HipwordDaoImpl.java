package com.joje.dbee.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joje.dbee.dao.HipwordDao;
import com.joje.dbee.vo.hipword.ArtistVo;
import com.joje.dbee.vo.hipword.SongRankVo;
import com.joje.dbee.vo.hipword.SongVo;

@Repository
public class HipwordDaoImpl implements HipwordDao {

	@Autowired
	private SqlSessionTemplate sst;
	
	@Override
	public int countArtistById(String artistId) {
		return sst.selectOne("hipword.countArtistById", artistId);
	}
	
	@Override
	public int countSongById(String songId) {
		return sst.selectOne("hipword.countSongById", songId);
	}
	
	@Override
	public int insertArtist(ArtistVo artist) {
		return sst.insert("hipword.insertArtist", artist);
	}

	@Override
	public int insertSong(Map<String, Object> song) {
		return sst.insert("hipword.insertSong", song);
	}

	@Override
	public SongVo selectOneSongById(String songId) {
		return sst.selectOne("selectOneSongById", songId);
	}

	@Override
	public int insertAllSongRank(List<SongRankVo> songs) {
		return sst.insert("insertAllSongRank", songs);
	}

	@Override
	public List<SongRankVo> selectAllSongRankByDate(String now) {
		return sst.selectList("selectAllSongRankByDate", now);
	}

}
