package com.joje.dbee.controller.rest;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.joje.dbee.service.FileStorageService;
import com.joje.dbee.service.HipwordService;
import com.joje.dbee.service.HttpRequestService;
import com.joje.dbee.vo.common.ResultVo;
import com.joje.dbee.vo.hipword.AlbumVo;
import com.joje.dbee.vo.hipword.ArtistVo;
import com.joje.dbee.vo.hipword.SongVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/dbee/hipword/api")
public class HipwordRestController {

	@Autowired
	private HttpRequestService httpRequestService;

	@Autowired
	private HipwordService hipwordService;

	private static final String MELON_URL = "https://www.melon.com/chart/index.htm";

	private final Map<String, String> URL_MAP;

	private Gson gson = new Gson();

	public HipwordRestController() {
//		URL 정보 세팅
		this.URL_MAP = new HashMap<>();
		URL_MAP.put("melon.chart", "https://www.melon.com/chart/index.htm");
		URL_MAP.put("melon.search", "https://www.melon.com/search/total/index.htm?q=");
		URL_MAP.put("melon.song", "https://www.melon.com/song/detail.htm?songId=");
	}

	@GetMapping(value = "/chart", produces = "application/json; charset=utf8")
	public ResponseEntity<?> getChart() throws Exception {

		List<SongVo> songs = hipwordService.getCartList(httpRequestService.requestHtml(URL_MAP.get("melon.chart")));

		ResultVo resultVo = new ResultVo();
		resultVo.put("songs", songs);

		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}

	@GetMapping(value = "/get/{type}", produces = "application/json; charset=utf8")
	public ResponseEntity<?> searchSongByKeword(@PathVariable(value = "type") String type,
												@RequestParam(value = "keyword") String keyword) throws Exception {

//		Song 아이디 조회
		String searchUrl = URL_MAP.get("melon.search") + keyword;
		String songId = hipwordService.getSongIdByKeyword(httpRequestService.requestHtml(searchUrl));

		log.debug("[songId]=[{}]", songId);
		
		SongVo song = new SongVo();
		
		if(songId != null && songId.length() > 0) {
			Document detailDoc = httpRequestService.requestHtml(URL_MAP.get("melon.song") + songId);

			String songTitle = hipwordService.getSongTitleByMelon(detailDoc);
			ArtistVo artist = hipwordService.getArtistByMelon(detailDoc);
			AlbumVo album = hipwordService.getAlbumByMelon(detailDoc);
			List<String> lyrics = hipwordService.getLyricsByMelon(detailDoc);
			
			song.setSongId(songId);
			song.setSongTitle(songTitle);
			song.setLyrics(lyrics);
			song.setAlbum(album.getAlbumName());
			song.setArtist(artist.getArtistName());
		}

		ResultVo resultVo = new ResultVo();
		resultVo.put("song", song);

		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
}
