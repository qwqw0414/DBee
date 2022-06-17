package com.joje.dbee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.joje.dbee.service.HipwordService;
import com.joje.dbee.vo.hipword.SongVo;

@Service(value = "HipwordService")
public class HipwordServiceImpl implements HipwordService {

	@Override
	public List<SongVo> getCartList(Document doc) {
		List<SongVo> songs = new ArrayList<>();
		
		Elements elmts = doc.select("tbody tr");
		
		for(Element elmt : elmts) {
			String album = elmt.selectFirst("a").attr("title");
			String rank = elmt.selectFirst("span.rank").text();
			String id = elmt.attr("data-song-no");
			String title = elmt.selectFirst(".wrap_song_info .rank01 a").text();
			String singer = elmt.selectFirst(".wrap_song_info .rank02 a").text();
			
			SongVo vo = new SongVo();
			vo.setSingId(Integer.parseInt(id));
			vo.setSingTitle(title);
			vo.setAlbum(album);
			vo.setSinger(singer);
			
			songs.add(vo);
		}
		
		return songs;
	}

}
