package com.joje.dbee.service;

import java.util.List;
import java.util.Map;

import com.joje.dbee.vo.home.HomeMenuVo;

public interface JsonConfigService {

	public List<HomeMenuVo> getHomeMenu() throws RuntimeException;

	public String getRegexp(String key);

	public Map<String, String> getRegexp();

}
