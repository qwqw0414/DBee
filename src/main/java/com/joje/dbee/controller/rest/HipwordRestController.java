package com.joje.dbee.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.joje.dbee.common.utils.StringUtil;
import com.joje.dbee.component.HttpRequestComponent;
import com.joje.dbee.service.HipwordService;
import com.joje.dbee.vo.common.ResultVo;
import com.joje.dbee.vo.hipword.SongRankVo;
import com.joje.dbee.vo.hipword.SongVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/dbee/hipword/api")
public class HipwordRestController {

	@Autowired
	private HttpRequestComponent httpRequestComponent;

	@Autowired
	private HipwordService hipwordService;

	private final Map<String, String> URL_MAP = new HashMap<>();
	private Gson gson = new Gson();

	public HipwordRestController() {
//		URL 정보 세팅
		URL_MAP.put("melon.chart", "https://www.melon.com/chart/index.htm");
		URL_MAP.put("melon.search", "https://www.melon.com/search/total/index.htm?q=");
		URL_MAP.put("melon.song", "https://www.melon.com/song/detail.htm?songId=");
	}

	@GetMapping(value = "/test", produces = "application/json; charset=utf8")
	public ResponseEntity<?> test(@RequestParam String keyword) throws Exception {

		String id = hipwordService.getSongIdToMelon(keyword);
		log.debug("[id]=[{}]", id);
		
//		결과 셋
		ResultVo resultVo = new ResultVo();
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
	@GetMapping(value = "/chart/{type}", produces = "application/json; charset=utf8")
	public ResponseEntity<?> getChart(@PathVariable(value = "type") String type) throws Exception {

		List<SongRankVo> songs = hipwordService.getChartListToMelon();

		ResultVo resultVo = new ResultVo();
		resultVo.put("songs", songs);

		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}

	@GetMapping(value = "/{type}", produces = "application/json; charset=utf8")
	public ResponseEntity<?> searchSongByKeword(@PathVariable(value = "type") String type,
												@RequestParam(value = "keyword") String keyword) throws Exception {
		
//		Song 아이디 조회
		String songId = hipwordService.getSongIdToMelon(keyword);
		log.debug("[songId]=[{}]", songId);
		
		SongVo song = null;
		if(!StringUtil.isBlank(songId)) {
			song = hipwordService.selectOneSongById(songId);
			log.debug("[song]=[{}]", song);
			if(song == null) {
				hipwordService.insertSong(songId);
				song = hipwordService.selectOneSongById(songId);
			}
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.put("song", song);
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
}
