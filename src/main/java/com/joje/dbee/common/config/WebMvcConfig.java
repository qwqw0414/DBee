package com.joje.dbee.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.joje.dbee.common.interceptor.AccountInterceptor;
import com.joje.dbee.common.interceptor.CommonInterceptor;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private static final String[] EXCLUDE_PATHS = { "/assets/**", "/error", "/favicon.ico"};
	
	/**
	 * 인터셉터 핸들링
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
//		registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/**")
//														.excludePathPatterns(EXCLUDE_PATHS);
		
//		Account관련 데이터 유효성 처리 인터셉터
		registry.addInterceptor(new AccountInterceptor()).addPathPatterns("/dbee/account/**", 
																		  "/dbee/admin/account/**");
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	//	ViewResolver 설정
	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("classpath:templates/");
		resolver.setSuffix(".html");
		return resolver;
	}

//  resources 배치 파일 viewResolve 제외
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

}
