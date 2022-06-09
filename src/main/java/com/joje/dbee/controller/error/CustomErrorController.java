package com.joje.dbee.controller.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.joje.dbee.common.contents.StatusCode;

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
		String errorMessage = "";
		
		if ("404".equals(status)) {
			errorMessage = StatusCode.NOT_FOUND.getMessage();
		} else if ("403".equals(status)) {
			errorMessage = StatusCode.FORBIDDEN.getMessage();
		} else if ("500".equals(status)) {
			errorMessage = StatusCode.INTERNAL_SERVER_ERROR.getMessage();
		}
		
//		requset data set
		request.setAttribute("errorCode", status);
		request.setAttribute("errorMessage", errorMessage);
		
		log.info("ErrorController [status]=[{}]", status);
		
		return "error/error";
	}
	
}
