package com.joje.dbee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.joje.dbee.common.utils.DateUtil;
import com.joje.dbee.common.utils.StringUtil;
import com.joje.dbee.component.HttpRequestComponent;
import com.joje.dbee.dao.HipwordDao;
import com.joje.dbee.repository.HipwordRepository;
import com.joje.dbee.service.HipwordService;
import com.joje.dbee.vo.hipword.ArtistVo;
import com.joje.dbee.vo.hipword.SongRankVo;
import com.joje.dbee.vo.hipword.SongVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "HipwordService")
public class HipwordServiceImpl implements HipwordService {

	private final Map<String, String> URL_MAP = new HashMap<>();
	private Gson gson = new Gson();
	
	public HipwordServiceImpl() {
//		URL 정보 세팅
		URL_MAP.put("melon.chart", "https://www.melon.com/chart/index.htm");
		URL_MAP.put("melon.search", "https://www.melon.com/search/total/index.htm?q=");
		URL_MAP.put("melon.song", "https://www.melon.com/song/detail.htm?songId=");
	}

	@Autowired
	private HttpRequestComponent httpRequestComponent;

	@Autowired
	private HipwordRepository hipwordRepository;
	
	@Autowired
	private HipwordDao hipwordDao;
	
	private static Pattern regexp = Pattern.compile("\\d+");
	
	@Override
	public List<SongRankVo> getChartListToMelon() {
		List<SongRankVo> songs = new ArrayList<>();
		Document doc = httpRequestComponent.requestHtml(URL_MAP.get("melon.chart"));
		
		Elements elmts = doc.select("tbody tr");

		for (Element elmt : elmts) {
			String album = elmt.selectFirst("a").attr("title");
			int rank = Integer.parseInt(elmt.selectFirst("span.rank").text());
			String id = elmt.attr("data-song-no");
			String title = elmt.selectFirst(".wrap_song_info .rank01 a").text();
			String artist = elmt.selectFirst(".wrap_song_info .rank02 a").text();

			SongRankVo vo = new SongRankVo();
			vo.setSongId(id);
			vo.setSongTitle(title);
			vo.setArtist(artist);
			vo.setSongRank(rank);

			songs.add(vo);
		}
		return songs;
	}

	@Override
	public List<String> getLyricsToMelon(Document doc) {
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
	public String getSongIdToMelon(String keyword) {
		String searchUrl = URL_MAP.get("melon.search") + keyword;
		Document doc = httpRequestComponent.requestHtml(searchUrl);
		
		Elements tables = doc.select("#frm_songList table");
		Elements a = tables.eq(0).select("td.t_left a");
		
		String songId = "";
		if(a != null) {
			String href = a.attr("href");
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
	@Transactional
	public void insertSong(String songId) {
		if(hipwordDao.countSongById(songId) < 1) {
			Document doc = httpRequestComponent.requestHtml(URL_MAP.get("melon.song") + songId);
			
			ArtistVo artist = this.getArtistByMelon(doc);
			log.debug("[artist]=[{}]", artist);
			if(hipwordDao.countArtistById(artist.getArtistId()) < 1) {
				int result = hipwordDao.insertArtist(artist);
			}
			
			Map<String, Object> song = new HashMap<>();
			song.put("songId", songId);
			song.put("songTitle", this.getSongTitleByMelon(doc));
			song.put("artistId", artist.getArtistId());
			song.put("lyrics", StringUtil.toStr(this.getLyricsToMelon(doc), "\n"));
			
			hipwordDao.insertSong(song);
		}
	}

	@Override
	public SongVo selectOneSongById(String songId) {
		return hipwordDao.selectOneSongById(songId);
	}

	@Override
	public int insertSongRank(List<SongRankVo> songs) {
		return hipwordDao.insertAllSongRank(songs);
	}

	@Override
	public List<SongRankVo> selectAllSongRankByDate(String now) {
		return hipwordDao.selectAllSongRankByDate(now);
	}

}
