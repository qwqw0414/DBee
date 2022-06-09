package com.joje.dbee.controller.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@GetMapping(value = "/error")
	public String error(HttpServletRequest request) {
		String status = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		String errorStatus = "";
		String errorMessage = "";
		
		if ("404".equals(status)) {
			errorStatus = "Page Not Found";
			errorMessage = "페이지를 찾을 수 없습니다.";
		} else if ("403".equals(status)) {
			errorStatus = "Forbidden";
			errorMessage = "해당 페이지에 권한이 없습니다.";
		} else if ("500".equals(status)) {
			errorStatus = "Server Error";
			errorMessage = "서버 에러........... ??";
		}
		
//		requset data set
		request.setAttribute("errorCode", status);
		request.setAttribute("errorStatus", errorStatus);
		request.setAttribute("errorMessage", errorMessage);
		
		log.info("ErrorController [status]=[{}]", status);
		
		return "error/error";
	}
	
}
