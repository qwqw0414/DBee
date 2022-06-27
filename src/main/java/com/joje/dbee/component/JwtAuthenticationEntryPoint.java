package com.joje.dbee.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Override
	public void commence(HttpServletRequest request, 
						 HttpServletResponse response, 
						 AuthenticationException authException) throws IOException, ServletException {

        log.error("Responding with unauthorized error. Message - {}", authException.getMessage());

//        ErrorCode unAuthorizationCode = (ErrorCode) request.getAttribute("unauthorization.code");

//        request.setAttribute("response.failure.code", unAuthorizationCode.name());
        request.setAttribute("response.failure.code", "errorName");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "errorMessage");
	}

}
