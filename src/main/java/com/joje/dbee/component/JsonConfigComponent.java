package com.joje.dbee.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.joje.dbee.vo.home.HomeMenuVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(value = "JsonConfigComponent")
public class JsonConfigComponent {

	private final String JSON_PATH = this.getClass().getResource("/static/assets/json").getPath();

	private Gson gson = new Gson();

	@SuppressWarnings("unchecked")
	public List<HomeMenuVo> getHomeMenu() {
		List<HomeMenuVo> menus = null;
		try {
			String jsonStr = this.readJson("homeMenu.json");
			List<Map<String, Object>> jsonData = gson.fromJson(jsonStr, List.class);
			menus = new ArrayList<>();
			
			for (Map<String, Object> map : jsonData) {
				HomeMenuVo vo = new HomeMenuVo();
				vo.setMenuNo(Long.parseLong(String.valueOf(map.get("menuNo"))));
				vo.setMenuTitle((String) map.get("menuTitle"));
				vo.setMenuImage((String) map.get("menuImage"));
				vo.setRequestUrl((String) map.get("requestUrl"));
				menus.add(vo);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		log.debug("[menus]=[{}]", menus);
		return menus;
	}

	@SuppressWarnings("unchecked")
	public String readJson(String fileName) {

		String path = JSON_PATH + File.separator + fileName;
		String jsonStr = "";

		log.info("[path]=[{}]", path);
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				jsonStr += line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonStr;
	}

}
