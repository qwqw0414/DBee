package com.joje.dbee.service;

import java.util.List;

import com.joje.dbee.vo.home.HomeMenuVo;

public interface JsonConfigService {

	public List<HomeMenuVo> getHomeMenu() throws RuntimeException;

}
