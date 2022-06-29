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

/**
 * 공통 에러 핸들러 
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

	private static HttpHeaders header;

	public ExceptionAdvice() {
		header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
	}

	@ExceptionHandler(value = { DBeeException.class })
	public ResponseEntity<ResultVo> dbeeException(DBeeException e) {
		log.error(e.getMessage());

		StatusCode statusCode = e.getStatus() == null ? StatusCode.FAILED_ERROR : e.getStatus();
		ResultVo resultVo = new ResultVo(statusCode);
		resultVo.put("message", statusCode.getMessage());

		return new ResponseEntity<>(resultVo, header, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { HttpRequestException.class })
	public ResponseEntity<ResultVo> httpRequestException(HttpRequestException e) {
		log.error(e.getMessage());

		ResultVo resultVo = new ResultVo(StatusCode.FAILED_CONNECT);
		resultVo.put("message", StatusCode.FAILED_CONNECT.getMessage());

		return new ResponseEntity<>(resultVo, header, HttpStatus.OK);
	}

	@ExceptionHandler(value = { BadRequestException.class })
	public ResponseEntity<ResultVo> badRequestException(BadRequestException e) {
		log.error(e.getMessage());

		ResultVo resultVo = new ResultVo(StatusCode.BAD_REQUEST);
		resultVo.put("message", e.getMessage());

		return new ResponseEntity<>(resultVo, header, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<ResultVo> methodArgumentNotValidExceptionException(MethodArgumentNotValidException e) {
		log.error(e.getMessage());

		ResultVo resultVo = new ResultVo(StatusCode.BAD_REQUEST);

		return new ResponseEntity<>(resultVo, header, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { AccessDeniedException.class })
	public ResponseEntity<ResultVo> accessDeniedException(AccessDeniedException e) {
		log.error(e.getMessage());

		ResultVo resultVo = new ResultVo(StatusCode.FORBIDDEN);

		return new ResponseEntity<>(resultVo, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value = { RuntimeException.class })
	public ResponseEntity<ResultVo> runtimeException(RuntimeException e) {
		log.error(e.getMessage());
		e.printStackTrace();

		ResultVo resultVo = new ResultVo(StatusCode.INTERNAL_SERVER_ERROR);

		return new ResponseEntity<>(resultVo, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
