package com.joje.dbee.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.joje.dbee.service.HipwordService;
import com.joje.dbee.vo.hipword.SongRankVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableAsync
public class HipwordSchedule {

	@Autowired
	private HipwordService hipwordService;

//	첫번째 부터 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
	@Scheduled(cron = "0 * * * * ?", zone = "Asia/Seoul")
	public void songRankUpdateSchedule() {
		log.info("Start Schedule");

		List<SongRankVo> songs = hipwordService.getChartListToMelon();

		if (songs != null && songs.size() > 0)
			for (SongRankVo vo : songs)
				hipwordService.insertSong(vo.getSongId());

		hipwordService.insertSongRank(songs);

		log.info("End Schedule");
	}

}
