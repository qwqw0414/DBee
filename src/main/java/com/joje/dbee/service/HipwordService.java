package com.joje.dbee.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.joje.dbee.vo.hipword.AlbumVo;
import com.joje.dbee.vo.hipword.ArtistVo;
import com.joje.dbee.vo.hipword.SongVo;

public interface HipwordService {

	public List<SongVo> getCartList(Document doc);

	public List<String> getLyricsByMelon(Document doc);

	public String getSongIdByKeyword(Document doc);

	public String getSongTitleByMelon(Document doc);

	public ArtistVo getArtistByMelon(Document doc);

	public AlbumVo getAlbumByMelon(Document doc);

}
