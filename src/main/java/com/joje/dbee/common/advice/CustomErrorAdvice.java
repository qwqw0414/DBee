package com.joje.dbee.common.advice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.joje.dbee.common.contents.StatusCode;
import com.joje.dbee.exception.BadRequestException;
import com.joje.dbee.exception.DBeeException;
import com.joje.dbee.exception.HttpRequestException;
import com.joje.dbee.vo.common.ResultVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CustomErrorAdvice {

	private Gson gson = new Gson();
	
	@ResponseBody
	@ExceptionHandler(DBeeException.class)
	public ResponseEntity<?> dbeeException(DBeeException e) {

//		결과 셋
		ResultVo resultVo = new ResultVo(e.getStatus());
		resultVo.put("message", e.getStatus().getMessage());
		log.info("[resultVo]=[{}]", resultVo);
		log.error(e.getMessage());

//		헤더 설정
		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8");

		return new ResponseEntity<>(gson.toJson(resultVo), resHeaders, HttpStatus.OK);
	}
	
	@ResponseBody
	@ExceptionHandler(HttpRequestException.class)
	public ResponseEntity<?> httpRequestException(HttpRequestException e) {

//		결과 셋
		ResultVo resultVo = new ResultVo(StatusCode.FAILED_CONNECT);
		resultVo.put("message", StatusCode.FAILED_CONNECT.getMessage());
		log.info("[resultVo]=[{}]", resultVo);
		log.error(e.getMessage());

//		헤더 설정
		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8");

		return new ResponseEntity<>(gson.toJson(resultVo), resHeaders, HttpStatus.OK);
	}

	/**
	 * 잘못된 요청시 발생하는 에러 핸들링
	 * @param e
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequestException(BadRequestException e) {
		log.error(e.getMessage());
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public String runtimeException(RuntimeException e, HttpServletRequest request, HttpServletResponse response) {

		log.error("runtimeException : {}", e.getMessage());
		
		e.printStackTrace();
		request.setAttribute("errorCode", "500");
		request.setAttribute("errorMessage", StatusCode.INTERNAL_SERVER_ERROR.getMessage());
		return "error/error";
	}
	
}
