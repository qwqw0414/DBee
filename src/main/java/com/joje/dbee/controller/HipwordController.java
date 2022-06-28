package com.joje.dbee.controller;

import java.util.List;

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
import com.joje.dbee.entity.hipword.SongEntity;
import com.joje.dbee.entity.hipword.RankEntity;
import com.joje.dbee.service.HipwordService;
import com.joje.dbee.vo.ResultVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/dbee/hipword/api")
public class HipwordController {

	@Autowired
	private HipwordService hipwordService;

	private Gson gson = new Gson();

	@GetMapping(value = "/test", produces = "application/json; charset=utf8")
	public ResponseEntity<?> test() throws Exception {

		List<RankEntity> ranks = hipwordService.getChartListToMelon();

		if (ranks != null && ranks.size() > 0)
			for (RankEntity vo : ranks)
				hipwordService.addSong(vo.getSong().getSongId());
		
		hipwordService.addRank(ranks);
		
//		결과 셋
		ResultVo resultVo = new ResultVo();
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}

	@GetMapping(value = "/{type}", produces = "application/json; charset=utf8")
	public ResponseEntity<?> searchSongByKeword(@PathVariable(value = "type") String type,
												@RequestParam(value = "keyword") String keyword) throws Exception {
		
//		Song 아이디 조회
		String songId = hipwordService.getSongIdToMelon(keyword);
		log.debug("[songId]=[{}]", songId);
		
		SongEntity song = null;
		if(!StringUtil.isBlank(songId)) {
			song = hipwordService.addSong(songId);
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.put("song", song);
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
}
