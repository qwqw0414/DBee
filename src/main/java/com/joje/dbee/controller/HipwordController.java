package com.joje.dbee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.joje.dbee.common.contents.StatusCode;
import com.joje.dbee.common.utils.ParamUtils;
import com.joje.dbee.common.utils.StringUtil;
import com.joje.dbee.dto.hipword.SongDto;
import com.joje.dbee.exception.DBeeException;
import com.joje.dbee.service.HipwordService;
import com.joje.dbee.service.NluService;
import com.joje.dbee.vo.ResultVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Lyrics 크롤링 컨트롤러
 * @author kpcnc
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/dbee/hipword")
public class HipwordController {

	private final NluService nluService;
	private final HipwordService hipwordService;
	private final Gson gson;

	@GetMapping(value = "/test", produces = "application/json; charset=utf8")
	public ResponseEntity<?> test() throws Exception {
		
		List<String> nnList =  nluService.analyzeAllByNN(hipwordService.getLyricsList());
		Map<String, Integer> nnMap = new HashMap<>();
		
		for(String nn : nnList) {
			if(nnMap.containsKey(nn)) {
				nnMap.put(nn, nnMap.get(nn) + 1);
			} else {
				nnMap.put(nn, 1);
			}
		}
		
//		결과 셋
		ResultVo resultVo = new ResultVo();
		resultVo.put("nns", nnMap);
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}

	@GetMapping(value = "/{type}", produces = "application/json; charset=utf8")
	public ResponseEntity<ResultVo> searchSongByKeword(@PathVariable(value = "type") String type,
													   @RequestBody Map<String, Object> body) throws Exception {
		
//		검색 키워드
		String keyword = ParamUtils.toStr(body, "keyword");
		SongDto songDto = null;
		
		if("melon".equals(type)) {
			String songId = hipwordService.getSongIdToMelon(keyword);
			
			log.debug("[songId]=[{}]", songId);
			
			if (!StringUtil.isBlank(songId)) {
				songDto = hipwordService.getSongById(songId);
				log.debug("[songDto]=[{}]", songDto);
			} else {
				throw new DBeeException(StatusCode.FAILED_NO_DATA, "검색 결과가 없습니다.");
			}
			
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.put("song", songDto);
		
		return new ResponseEntity<>(resultVo, HttpStatus.OK);
	}
}
