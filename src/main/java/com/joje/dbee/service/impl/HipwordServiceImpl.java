package com.joje.dbee.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.joje.dbee.service.HipwordService;
import com.joje.dbee.vo.hipword.AlbumVo;
import com.joje.dbee.vo.hipword.ArtistVo;
import com.joje.dbee.vo.hipword.SongVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "HipwordService")
public class HipwordServiceImpl implements HipwordService {

	private static Pattern regexp = Pattern.compile("\\d+");
	
	@Override
	public List<SongVo> getCartList(Document doc) {
		List<SongVo> songs = new ArrayList<>();

		Elements elmts = doc.select("tbody tr");

		for (Element elmt : elmts) {
			String album = elmt.selectFirst("a").attr("title");
			String rank = elmt.selectFirst("span.rank").text();
			String id = elmt.attr("data-song-no");
			String title = elmt.selectFirst(".wrap_song_info .rank01 a").text();
			String artist = elmt.selectFirst(".wrap_song_info .rank02 a").text();

			SongVo vo = new SongVo();
			vo.setSongId(id);
			vo.setSongTitle(title);
			vo.setAlbum(album);
			vo.setArtist(artist);

			songs.add(vo);
		}

		return songs;
	}

	@Override
	public List<String> getLyricsByMelon(Document doc) {
		String FILTER_STR = "<!-- height:auto; 로 변경시, 확장됨 -->";

		List<String> lyrics = new ArrayList<>();
		
		Element elmt = doc.selectFirst("div.lyric");
		
		if (elmt != null) {
			String html = elmt.html().replace(FILTER_STR, "");
			String[] line = html.split("<br>");
			for (String l : line) {
				lyrics.add(l.trim());
			}
		}
		
		return lyrics;
	}

	@Override
	public String getSongIdByKeyword(Document doc) {
		
		Element elmt = doc.selectFirst("#frm_songList tbody tr a");
		String songId = "";
		
		if(elmt != null) {
			String href = elmt.attr("href");
			Matcher matcher = regexp.matcher(href);
			
			if(matcher.find()) {
				songId = matcher.group();
			}
		}
		
		return songId;
	}

	@Override
	public String getSongTitleByMelon(Document doc) {

		Element elmt = doc.selectFirst("div.song_name");
		String songTitle = "";

		if (elmt != null) {
			songTitle = elmt.text().replace("곡명", "").trim();
		}

		return songTitle;
	}

	@Override
	public ArtistVo getArtistByMelon(Document doc) {
		
		ArtistVo artist = new ArtistVo();
		
		Element elmt = doc.selectFirst("div.artist");
		
//		가수명 조회
		artist.setArtistName(elmt.selectFirst("span").text().trim());
		
//		가수 아이디 조회
		String href = elmt.selectFirst("a").attr("href");
		Matcher matcher = regexp.matcher(href);
		if(matcher.find()) {
			artist.setArtistId(matcher.group());
		}
		
		log.debug("[artist]=[{}]", artist);
		return artist;
	}
	
	
	@Override
	public AlbumVo getAlbumByMelon(Document doc) {
		AlbumVo album = new AlbumVo();
		
		Element elmt = doc.selectFirst("div.entry div.meta dd");
		
//		앨범 명
		album.setAlbumName(elmt.selectFirst("a").text().trim());		
		
//		앨범 아이디
		String href = elmt.selectFirst("a").attr("href");
		Matcher matcher = regexp.matcher(href);
		if(matcher.find()) {
			album.setAlbumId(matcher.group());
		}
		
		log.debug("[album]=[{}]", album);
		return album;
	}
	
	
}
