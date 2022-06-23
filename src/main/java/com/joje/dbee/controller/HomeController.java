package com.joje.dbee.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.joje.dbee.common.utils.ParamUtil;
import com.joje.dbee.component.JsonConfigComponent;
import com.joje.dbee.service.MenuService;
import com.joje.dbee.vo.ResultVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/dbee")
public class HomeController {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private JsonConfigComponent jsonConfigComponent;
	
	private Gson gson = new Gson();
	
	@PostMapping(value = "/test",produces = "application/json; charset=utf8")
	@ResponseBody 
	public ResponseEntity<?> test(@RequestBody Map<String, Object> body) throws Exception {
		
		String string = ParamUtil.toStr(body.get("valueString"), "123");
		
		log.debug("[body]=[{}]", body);
		log.debug("[valueString]=[{}]", string);
		
		
		ResultVo resultVo = new ResultVo(); 
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
	@GetMapping(value = "")
	public ModelAndView viewHomePage(ModelAndView mav) throws Exception {
		
		ResultVo resultVo = new ResultVo();
		resultVo.put("menus", jsonConfigComponent.getHomeMenu());
		
		mav.setViewName("index");
		mav.addObject(resultVo);

		log.debug("[resultVo]=[{}]", resultVo);
		
		return mav;
	}
	
}
