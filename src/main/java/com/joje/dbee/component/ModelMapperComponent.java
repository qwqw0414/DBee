package com.joje.dbee.component;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component(value = "ModelMapperComponent")
public class ModelMapperComponent {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
