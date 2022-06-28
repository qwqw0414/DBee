package com.joje.dbee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.joje.dbee.vo.ResultVo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/dbee")
public class DbeeController {

	private final Gson gson;
	
	@GetMapping(value = "")
	public ResponseEntity<?> connectCheck() throws Exception {
		
		ResultVo resultVo = new ResultVo();
		resultVo.put("message", "Connect Complate !!");
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
}
