package com.joje.dbee.component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(value = "JsonDataComponent")
public class JsonDataComponent {

	private Gson gson = new Gson();
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> get(String fileName) {
		
		String path = this.getClass().getResource("/static/assets/json/" + fileName).getPath();
		Map<String, Object> result = null;
		
		File file = new File(path);
		log.info("[path]=[{}]", path);		
		
		try {
			result = gson.fromJson(new FileReader(file), Map.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		log.info("[result]=[{}]", result);
		return result;
	}
	
}
