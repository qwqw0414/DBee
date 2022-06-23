package com.joje.dbee.schedule;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.joje.dbee.entity.hipword.RankEntity;
import com.joje.dbee.service.HipwordService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableAsync
public class HipwordSchedule {

	@Autowired
	private HipwordService hipwordService;

//	첫번째 부터 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
//	@Scheduled(cron = "0 * * * * ?", zone = "Asia/Seoul")
	public void songRankUpdateSchedule() {
		log.info("Start Schedule");

		List<RankEntity> ranks = hipwordService.getChartListToMelon();

		if (ranks != null && ranks.size() > 0)
			for (RankEntity vo : ranks)
				hipwordService.addSong(vo.getSong().getSongId());
		
		hipwordService.addRank(ranks);

		log.info("End Schedule");
	}

}
