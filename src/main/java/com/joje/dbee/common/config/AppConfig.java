package com.joje.dbee.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * 공통 모듈
 *
 */
@Component
public class AppConfig {

	@Bean
	public Gson gson() {
		return new Gson();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
