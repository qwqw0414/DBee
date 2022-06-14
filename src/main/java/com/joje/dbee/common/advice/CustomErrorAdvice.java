package com.joje.dbee.common.advice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.gson.Gson;
import com.joje.dbee.common.contents.StatusCode;
import com.joje.dbee.exception.DBeeException;
import com.joje.dbee.vo.common.ResultVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CustomErrorAdvice {

	private Gson gson = new Gson();

	@ExceptionHandler(DBeeException.class)
	public ResponseEntity<?> customErrorException(DBeeException e) {

		ResultVo resultVo = new ResultVo(e.getStatus());
		resultVo.put("message", e.getMessage());
		log.info("[resultVo]=[{}]", resultVo);
		log.error(e.getMessage());

		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<>(gson.toJson(resultVo), resHeaders, HttpStatus.OK);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> customErrorException(RuntimeException e) {

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("errorCode", StatusCode.INTERNAL_SERVER_ERROR.getCode());
		resultMap.put("errorMessage", StatusCode.INTERNAL_SERVER_ERROR.getMessage());

		log.info("[ERROR:500]=[{}]", resultMap);
		log.error(e.getMessage());
		e.printStackTrace();

		return new ResponseEntity<>(gson.toJson(resultMap), HttpStatus.INTERNAL_SERVER_ERROR);
	}
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");

}
