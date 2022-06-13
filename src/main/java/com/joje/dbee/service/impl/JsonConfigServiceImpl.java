package com.joje.dbee.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joje.dbee.component.JsonConfigComponent;
import com.joje.dbee.service.JsonConfigService;
import com.joje.dbee.vo.home.HomeMenuVo;

@Service(value = "JsonConfigService")
public class JsonConfigServiceImpl implements JsonConfigService {

	private static Map<String, Object> configMap = Collections.synchronizedMap(new HashMap<>());

	@Autowired
	private JsonConfigComponent jsonConfigComponent;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeMenuVo> getHomeMenu() throws RuntimeException {
		final String MAP_KEY = "homeMenu";
		
		if(!configMap.containsKey(MAP_KEY) || configMap.get(MAP_KEY) == null)
			configMap.put(MAP_KEY, jsonConfigComponent.getHomeMenu());
		
		return (List<HomeMenuVo>) configMap.get(MAP_KEY);
	}
	
	
	
	
}
