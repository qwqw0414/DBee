package com.joje.dbee.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}

    @GetMapping("/error")
    public String redirctRoot(HttpServletRequest request) {
    	String status = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
    	
    	log.debug("[status]=[{}]", status);
    	
        return "index";
    }
	
}
