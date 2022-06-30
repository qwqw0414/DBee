package com.joje.dbee.controller;

import java.time.LocalDate;
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
import com.joje.dbee.common.utils.ParamUtil;
import com.joje.dbee.common.utils.StringUtil;
import com.joje.dbee.dto.hipword.RankDto;
import com.joje.dbee.dto.hipword.SongDto;
import com.joje.dbee.entity.hipword.RankEntity;
import com.joje.dbee.exception.DBeeException;
import com.joje.dbee.service.HipwordService;
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

	private final HipwordService hipwordService;
	private final Gson gson;

	@GetMapping(value = "/test", produces = "application/json; charset=utf8")
	public ResponseEntity<?> test() throws Exception {
		LocalDate recentDate = hipwordService.getRecentRankDate();
		if(recentDate != LocalDate.now()) {
			List<RankDto> ranks = hipwordService.getChartListToMelon();

			if (ranks != null && ranks.size() > 0)
				for (RankDto rank : ranks)
					hipwordService.getSongById(rank.getSong().getSongId());
			
			hipwordService.addRank(ranks);
		}
//		결과 셋
		ResultVo resultVo = new ResultVo();
		resultVo.put("date", recentDate);
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}

	@GetMapping(value = "/{type}", produces = "application/json; charset=utf8")
	public ResponseEntity<ResultVo> searchSongByKeword(@PathVariable(value = "type") String type,
												@RequestBody Map<String, Object> body) throws Exception {
		
//		검색 키워드
		String keyword = ParamUtil.toStr(body, "keyword");
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
