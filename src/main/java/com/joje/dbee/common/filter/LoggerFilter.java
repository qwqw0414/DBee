package com.joje.dbee.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.joje.dbee.common.utils.HttpUtil;
import com.joje.dbee.common.utils.ReadableRequestWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(urlPatterns = "/*")
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

		if (isInclude(url)) {
			// Request Logging
			log.info("=================== >> {} START {} >> ===================", method, url);
			log.debug("ContentType : {}", contentType);
			if (contentType != null && contentType.startsWith("application/json")) {
				ReadableRequestWrapper requestWrapper = new ReadableRequestWrapper((HttpServletRequest) request);
				log.debug("RequestBody : {}", HttpUtil.getBody(requestWrapper));
			} else {
				log.debug("ParameterMap : {}", gson.toJson(request.getParameterMap()));
			}

			chain.doFilter(request, response);

			log.debug("=================== << {} END {} << ===================\n", method, url);
		} else {
			chain.doFilter(request, response);
		}
	}

//	Exclude URL 유효성 검사
	private boolean isInclude(String url) {
		for (String i : EXCLUDE_PATHS) {
			if (url.contains(i))
				return false;
		}
		return true;
	}

}
