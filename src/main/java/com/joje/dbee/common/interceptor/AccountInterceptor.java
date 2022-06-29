package com.joje.dbee.common.interceptor;

import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.google.gson.Gson;
import com.joje.dbee.common.contents.StatusCode;
import com.joje.dbee.common.utils.HttpUtil;
import com.joje.dbee.common.utils.ReadableRequestWrapper;
import com.joje.dbee.component.JsonConfigComponent;
import com.joje.dbee.exception.BadRequestException;
import com.joje.dbee.exception.DBeeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(value = "AccountInterceptor")
public class AccountInterceptor implements HandlerInterceptor {
	
	private JsonConfigComponent jsonComp = new JsonConfigComponent();
	private Gson gson = new Gson();
	
	private final String REG_USER_ID = jsonComp.getRegexp("account.userId");
	private final String REG_USER_NAME = jsonComp.getRegexp("account.userName");
	private final String REG_PASSWORD = jsonComp.getRegexp("account.password");
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		ReadableRequestWrapper requestWrapper = new ReadableRequestWrapper(request);
		
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
//				log.debug("Invalid UserId !! [userId]=[{}]", userId);
				throw new BadRequestException("Invalid Value : userId");
			}
			
			if(userName != null && !Pattern.matches(REG_USER_NAME, userName)) {
//				log.debug("Invalid UserName !! [userName]=[{}]", userName);
				throw new BadRequestException("Invalid Value : userName");
			}
			
			if(password != null && !Pattern.matches(REG_PASSWORD, password)) {
//				log.debug("Invalid Password !! [password]=[{}]", password);
				throw new BadRequestException("Invalid Value : password");
			}
		}
		
		return HandlerInterceptor.super.preHandle(requestWrapper, response, handler);
	}
	
}
