package com.joje.dbee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.joje.dbee.common.component.HttpRequestComponent;
import com.joje.dbee.common.utils.StringUtil;
import com.joje.dbee.dto.hipword.SongDto;
import com.joje.dbee.entity.hipword.ArtistEntity;
import com.joje.dbee.entity.hipword.SongEntity;
import com.joje.dbee.repository.hipword.ArtistRepository;
import com.joje.dbee.repository.hipword.RankRepository;
import com.joje.dbee.repository.hipword.SongRepository;
import com.joje.dbee.entity.hipword.RankEntity;
import com.joje.dbee.service.HipwordService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "HipwordService")
public class HipwordServiceImpl implements HipwordService {

	private final Map<String, String> URL_MAP = new HashMap<>();
	
	public HipwordServiceImpl() {
//		URL 정보 세팅
		URL_MAP.put("melon.chart", "https://www.melon.com/chart/index.htm");
		URL_MAP.put("melon.search", "https://www.melon.com/search/total/index.htm?q=");
		URL_MAP.put("melon.song", "https://www.melon.com/song/detail.htm?songId=");
	}

	@Autowired
	private HttpRequestComponent httpRequestComponent;

	@Autowired
	private SongRepository songRepository;

	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private RankRepository rankRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
//	아이디 매칭 패턴
	private static Pattern regexp = Pattern.compile("\\d+");
	
	@Override
	public List<RankEntity> getChartListToMelon() {
		List<RankEntity> ranks = new ArrayList<>();
		Document doc = httpRequestComponent.requestHtml(URL_MAP.get("melon.chart"));
		
		Elements elmts = doc.select("tbody tr");

		for (Element elmt : elmts) {
			String id = elmt.attr("data-song-no");
			int rank = Integer.parseInt(elmt.selectFirst("span.rank").text());

			SongEntity songEntity = new SongEntity();
			songEntity.setSongId(id);

			RankEntity rankEntity = new RankEntity();
			rankEntity.setSongRank(rank);
			rankEntity.setSong(songEntity);
			
			ranks.add(rankEntity);
		}
		return ranks;
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
	public ArtistEntity getArtistByMelon(Document doc) {
		ArtistEntity artist = new ArtistEntity();
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
	public SongDto getSongById(String songId) {
		
		SongEntity songEntity = null;
		
//		DB에 해당 곡 정보가 있는지 여부 판단
		if(songRepository.countBySongId(songId) < 1) {
//			없다면 크롤링
			Document doc = httpRequestComponent.requestHtml(URL_MAP.get("melon.song") + songId);
			ArtistEntity avo = this.getArtistByMelon(doc);
			ArtistEntity artist = new ArtistEntity();
			if (artistRepository.countByArtistId(avo.getArtistId()) < 1) {
				artist.setArtistId(avo.getArtistId());
				artist.setArtistName(avo.getArtistName());
				artist = artistRepository.save(artist);
			} else {
				artist = artistRepository.findByArtistId(avo.getArtistId());
			}
			
//			저장 정보 셋
			SongEntity song = new SongEntity();
			song.setSongId(songId);
			song.setSongTitle(this.getSongTitleByMelon(doc));
			song.setArtist(artist);
			song.setLyrics(StringUtil.join(this.getLyricsToMelon(doc), "\n"));
			
			songEntity = songRepository.save(song);
		} else {
			songEntity = songRepository.findBySongId(songId).get();
		}
		
		return modelMapper.map(songEntity, SongDto.class);
	}
	

	@Override
	public int addRank(List<RankEntity> ranks) {
		for(RankEntity rank : ranks) {
			
			log.debug("[rank]=[{}]", rank);
			String songId = rank.getSong().getSongId();
			
			SongEntity songEntity = songRepository.findBySongId(songId).get();
			rank.setSong(songEntity);
		}
		
		rankRepository.saveAll(ranks);
		return 0;
	}

}
