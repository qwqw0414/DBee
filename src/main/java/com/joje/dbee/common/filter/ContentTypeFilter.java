package com.joje.dbee.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.core.annotation.Order;

import com.joje.dbee.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(value = 2)
@WebFilter(urlPatterns = "/dbee/*")
public class ContentTypeFilter implements Filter{
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String contentType = request.getContentType();
		
		if (contentType == null || ! contentType.startsWith("application/json")) {
			throw new BadRequestException("잘못 된 ContentType");
		}
		
		chain.doFilter(request, response);
	}

}
