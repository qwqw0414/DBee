package com.joje.dbee.service.impl;

import java.time.LocalDate;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.joje.dbee.common.component.HttpRequestComponent;
import com.joje.dbee.common.utils.StringUtil;
import com.joje.dbee.dto.hipword.RankDto;
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
	private final String URL_MELON_CHART;
	private final String URL_MELON_SEARCH;
	private final String URL_MELON_DETAIL;
	
	public HipwordServiceImpl(@Value("${dbee.hipword.url.melon.chart}") String chart,
							  @Value("${dbee.hipword.url.melon.search}") String search,
							  @Value("${dbee.hipword.url.melon.detail}") String detail) {
		
		this.URL_MELON_CHART = chart;
		this.URL_MELON_SEARCH = search;
		this.URL_MELON_DETAIL = detail;
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
	
	/**
	 * 멜론 차트 TOP 100 데이터 크롤링
	 */
	@Override
	public List<RankDto> getChartListToMelon() {
		List<RankDto> ranks = new ArrayList<>();
		Document doc = httpRequestComponent.requestHtml(URL_MELON_CHART);
		
		Elements elmts = doc.select("tbody tr");

		for (Element elmt : elmts) {
			String id = elmt.attr("data-song-no");
			int rank = Integer.parseInt(elmt.selectFirst("span.rank").text());

			SongDto songDto = new SongDto();
			songDto.setSongId(id);

			RankDto rankDto = new RankDto();
			rankDto.setSongRank(rank);
			rankDto.setSong(songDto);
			
			ranks.add(rankDto);
		}
		return ranks;
	}
	
	/**
	 * 멜론 곡 아이디를 이용해 해당 곡 정보 크롤링
	 */
	@Override
	public SongDto getSongById(String songId) {

		SongEntity songEntity = songRepository.findBySongId(songId);

		if (songEntity == null) {

			Document doc = httpRequestComponent.requestHtml(URL_MELON_DETAIL + songId);

			ArtistEntity artistEntity = this.getArtistByMelon(doc);
			if (artistRepository.countByArtistId(artistEntity.getArtistId()) < 1) {
				artistEntity = artistRepository.save(artistEntity);
			} else {
				artistEntity = artistRepository.findByArtistId(artistEntity.getArtistId());
			}

			songEntity = new SongEntity();
			songEntity.setSongId(songId);
			songEntity.setSongTitle(this.getSongTitleByMelon(doc));
			songEntity.setLyrics(StringUtil.join(this.getLyricsToMelon(doc), "\n"));
			songEntity.setArtist(artistEntity);
			songEntity = songRepository.save(songEntity);
		}

		return modelMapper.map(songEntity, SongDto.class);
	}

	/**
	 * 곡 랭크 데이터 추가
	 */
	@Override
	public int addRank(List<RankDto> ranks) {
		List<RankEntity> saveRanks = new ArrayList<>();
		
		for(RankDto rank : ranks) {
			String songId = rank.getSong().getSongId();
			
			SongEntity songEntity = songRepository.findBySongId(songId);
			RankEntity rankEntity = modelMapper.map(rank, RankEntity.class);
			rankEntity.setRegDate(LocalDate.now());
			rankEntity.setSong(songEntity);
			saveRanks.add(rankEntity);
		}
		
		saveRanks = rankRepository.saveAll(saveRanks);
		return saveRanks.size();
	}

	/**
	 * 멜론에 해당 키워드로 검색 후 가장 일치하는 곡 아이디 리턴
	 */
	@Override
	public String getSongIdToMelon(String keyword) {
		String searchUrl = URL_MELON_SEARCH + keyword;
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

	/**
	 * 가장 최근 등록일 조회
	 */
	@Override
	public LocalDate getRecentRankDate() {
		LocalDate resultDate = rankRepository.findTopOrderByRegDate();
		if(resultDate == null) {
			resultDate = LocalDate.MIN;
		}
		return resultDate;
	}
	
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
	
	private String getSongTitleByMelon(Document doc) {
		Element elmt = doc.selectFirst("div.song_name");
		String songTitle = "";
		if (elmt != null) {
			songTitle = elmt.text().replace("곡명", "").trim();
		}
		return songTitle;
	}

	private ArtistEntity getArtistByMelon(Document doc) {
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
		return artist;
	}

	@Override
	public List<String> getLyricsList() {
		List<String> lyricsList = new ArrayList<>();
		
		List<SongEntity> songs = songRepository.findAll();
		
		for(SongEntity song : songs) {
			lyricsList.add(song.getLyrics());
		}
		
		return lyricsList;
	}
	
}
