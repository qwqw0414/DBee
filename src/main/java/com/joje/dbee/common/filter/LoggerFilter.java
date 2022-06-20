package com.joje.dbee.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;

import com.google.gson.Gson;
import com.joje.dbee.common.utils.HttpUtil;
import com.joje.dbee.common.utils.ReadableRequestWrapper;
import com.joje.dbee.common.utils.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(value = 0)
@WebFilter(urlPatterns = "/dbee/*")
public class LoggerFilter implements Filter{

	private static final String[] EXCLUDE_PATHS = { "assets", "favicon.ico" };
	private static Gson gson = new Gson();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String url = httpRequest.getRequestURI();
		String method = httpRequest.getMethod();
		String contentType = request.getContentType();

		if (StringUtil.isInclude(url, EXCLUDE_PATHS)) {
			// Request Logging
			log.info("=================== >> {} START {} >> ===================", method, url);
			log.debug("ContentType : {}", contentType);
			if (contentType != null && contentType.startsWith("application/json")) {
				ReadableRequestWrapper requestWrapper = new ReadableRequestWrapper((HttpServletRequest) request);
				log.debug("RequestBody : {}", HttpUtil.getBody(requestWrapper));
				chain.doFilter(requestWrapper, response);
			} else {
				log.debug("ParameterMap : {}", gson.toJson(request.getParameterMap()));
				chain.doFilter(request, response);
			}

			log.debug("=================== << {} END {} << ===================\n", method, url);
		} else {
			chain.doFilter(request, response);
		}
	}
}
