package com.joje.dbee.common.component;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.joje.dbee.common.utils.FileUtil;
import com.joje.dbee.vo.home.HomeMenuVo;

import lombok.extern.slf4j.Slf4j;

@Component(value = "JsonConfigComponent")
public class JsonConfigComponent {
	
	private final String JSON_PATH = this.getClass().getResource("/static/assets/json").getPath();
	private static Map<String, Object> configMap = Collections.synchronizedMap(new HashMap<>());
	
	private static final String KEY_MENU = "homeMenu";
	private static final String KEY_REGEXP = "regexp";
	
	private Gson gson = new Gson();
	
	@SuppressWarnings("unchecked")
	public List<HomeMenuVo> getHomeMenu() {
		
		if(!configMap.containsKey(KEY_MENU) || configMap.get(KEY_MENU) == null)
			this.setHomeMenu();
		
		return (List<HomeMenuVo>) configMap.get(KEY_MENU);
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getRegexp() {
		
		if(!configMap.containsKey(KEY_REGEXP) || configMap.get(KEY_REGEXP) == null)
			this.setRegexp();
		
		return (Map<String, String>) configMap.get(KEY_REGEXP);
	}
	
	public String getRegexp(String key) {
		return this.getRegexp().get(key) == null ? "" : this.getRegexp().get(key);
	}
	
	@SuppressWarnings("unchecked")
	public void setHomeMenu() {
		String jsonPath = JSON_PATH + File.separator + KEY_MENU + ".json";
		List<Map<String, Object>> jsonData = gson.fromJson(FileUtil.read(jsonPath), List.class);

		List<HomeMenuVo> menus = new ArrayList<>();
		for (Map<String, Object> map : jsonData) {
			HomeMenuVo vo = new HomeMenuVo();
			vo.setMenuNo(Long.parseLong(String.valueOf(map.get("menuNo"))));
			vo.setMenuTitle((String) map.get("menuTitle"));
			vo.setMenuImage((String) map.get("menuImage"));
			vo.setRequestUrl((String) map.get("requestUrl"));
			menus.add(vo);
		}
		
		configMap.put(KEY_MENU, jsonData);
	}

	@SuppressWarnings("unchecked")
	public void setRegexp() {
		String jsonPath = JSON_PATH + File.separator + KEY_REGEXP + ".json";
		Map<String, Object> regexpMap = gson.fromJson(FileUtil.read(jsonPath), Map.class);
		
		configMap.put(KEY_REGEXP, regexpMap);
	}

}
