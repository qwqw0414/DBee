package com.joje.dbee.common.advice;

import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.gson.Gson;
import com.joje.dbee.common.contents.StatusCode;
import com.joje.dbee.exception.BadRequestException;
import com.joje.dbee.exception.DBeeException;
import com.joje.dbee.exception.HttpRequestException;
import com.joje.dbee.vo.ResultVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

	private static HttpHeaders header;
	
	public ExceptionAdvice() {
		header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
	}
	
	@ExceptionHandler(value = {DBeeException.class})
	public ResponseEntity<ResultVo> dbeeException(DBeeException e) {

		log.error(e.getMessage());
		
		StatusCode statusCode = e.getStatus() == null ? StatusCode.FAILED_ERROR : e.getStatus();
		
//		결과 셋
		ResultVo resultVo = new ResultVo(statusCode);
		resultVo.put("message", statusCode.getMessage());

		return new ResponseEntity<>(resultVo, header, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {HttpRequestException.class})
	public ResponseEntity<ResultVo> httpRequestException(HttpRequestException e) {

		ResultVo resultVo = new ResultVo(StatusCode.FAILED_CONNECT);
		resultVo.put("message", StatusCode.FAILED_CONNECT.getMessage());
		log.info("[resultVo]=[{}]", resultVo);
		log.error(e.getMessage());

		return new ResponseEntity<>(resultVo, header, HttpStatus.OK);
	}

	@ExceptionHandler(value = { BadRequestException.class })
	public ResponseEntity<ResultVo> badRequestException(BadRequestException e) {
		
		ResultVo resultVo = new ResultVo(StatusCode.BAD_REQUEST);
		resultVo.put("message", e.getMessage());
		
		log.error(e.getMessage());
		
		return new ResponseEntity<>(resultVo, header, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<ResultVo> methodArgumentNotValidExceptionException(MethodArgumentNotValidException e) {
		ResultVo resultVo = new ResultVo(StatusCode.BAD_REQUEST);
		log.error(e.getMessage());
		return new ResponseEntity<>(resultVo, header, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ResultVo> accessDeniedException(AccessDeniedException e) {
		log.error(e.getMessage());
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResultVo> runtimeException(RuntimeException e, HttpServletRequest request, HttpServletResponse response) {

		log.error("runtimeException : {}", e.getMessage());
		
		e.printStackTrace();
		request.setAttribute("errorCode", "500");
		request.setAttribute("errorMessage", StatusCode.INTERNAL_SERVER_ERROR.getMessage());
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
