package com.joje.dbee.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(value = "CommonInterceptor")
public class CommonInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
							 HttpServletResponse response, 
							 Object handler) throws Exception {
		
		String requestURI = request.getRequestURI();
//		String requestIP = request.getRemoteAddr();
		
//		log.info("[IP]=[{}]", requestIP);
		
		if(requestURI.indexOf("/dbee") < 0) {
			log.info("Invalid URI [URI]=[{}]", requestURI);
			response.sendRedirect("/dbee");
			return false;
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}
