package com.joje.dbee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.joje.dbee.component.JsonConfigComponent;
import com.joje.dbee.service.JsonConfigService;
import com.joje.dbee.service.MenuService;
import com.joje.dbee.vo.common.ResultVo;
import com.joje.dbee.vo.home.HomeMenuVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/dbee")
public class HomeController {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private JsonConfigService jsonConfigService;
	
	@GetMapping(value = "")
	public ModelAndView viewHomePage(ModelAndView mav) throws Exception {
		
		ResultVo resultVo = new ResultVo();
		resultVo.put("menus", jsonConfigService.getHomeMenu());
		
		mav.setViewName("index");
		mav.addObject(resultVo);

		log.info("[resultVo]=[{}]", resultVo);
		
		return mav;
	}
	
}