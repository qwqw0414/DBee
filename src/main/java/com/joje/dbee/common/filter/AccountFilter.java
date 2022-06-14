package com.joje.dbee.common.filter;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

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
//@WebFilter(urlPatterns = { "/dbee/account/signup", "/dbee/admin/account/update" })
public class AccountFilter implements Filter{
//	아이디 정규식 : 영문 숫자 6이상 20이하
	private static final String REG_USER_ID = "^[a-z]+[a-z0-9]{5,19}$";

//	성명 정규식 : 영어 한글 2이상 8이하
	private static final String REG_USER_NAME = "^[a-z가-힣]{2,8}$";

//	비밀번호 정규식 : 최소 8자리에 숫자, 문자, 특수문자 각각 1개 이상 포함
	private static final String REG_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
	
	private Gson gson = new Gson();
	
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		ReadableRequestWrapper requestWrapper = new ReadableRequestWrapper((HttpServletRequest) request);
		
		String contentType = requestWrapper.getContentType();
		String methode = requestWrapper.getMethod();
		
		String userId = null;
		String userName = null;
		String password = null;
		
		if(methode != null && "POST".equals(methode)) {
			if(contentType.startsWith("application/json")) {
				Map<String, Object> body = gson.fromJson(HttpUtil.getBody(requestWrapper), Map.class);
				userId = (String) body.get("userId");
				userName = (String) body.get("userName");
				password = (String) body.get("password");
			}
			else if (contentType.startsWith("application/x-www-form-urlencoded")){
				userId = requestWrapper.getParameter("userId");
				userName = requestWrapper.getParameter("userName");
				password = requestWrapper.getParameter("password");
			}
			
//			유효성 검증
			if(userId != null && !Pattern.matches(REG_USER_ID, userId)) {
				log.info("Invalid UserId !! [userId]=[{}]", userId);
				return;
			}
			
			if(userName != null && !Pattern.matches(REG_USER_NAME, userName)) {
				log.info("Invalid UserName !! [userName]=[{}]", userName);
				return;
			}
			
			if(password != null && !Pattern.matches(REG_PASSWORD, password)) {
				log.info("Invalid Password !! [password]=[{}]", password);
				return;
			}
		}
		
		chain.doFilter(requestWrapper, response);
	}
	
}
