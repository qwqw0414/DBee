package com.joje.dbee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.joje.dbee.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/dbee")
public class HomeController {

	@Autowired
	private MenuService menuService;
	
	@GetMapping(value = "")
	public ModelAndView viewHomePage(ModelAndView mav) throws Exception {
		
		mav.setViewName("index");
		
		return mav;
	}
	
}
