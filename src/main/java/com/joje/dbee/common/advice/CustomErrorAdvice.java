package com.joje.dbee.common.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CustomErrorAdvice {
	
	private Gson gson = new Gson();
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> customErrorException(RuntimeException e){
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", "faild");
		resultMap.put("errorCode", "500");
		resultMap.put("errorMessage", "서버 에러");
		
		log.info("[ERROR:500]=[{}]", resultMap);
		log.error(e.getMessage());
		e.printStackTrace();
		
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
		return new ResponseEntity<>(gson.toJson(resultMap), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
