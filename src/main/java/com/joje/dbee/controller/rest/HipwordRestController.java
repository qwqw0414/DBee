package com.joje.dbee.controller.rest;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.joje.dbee.service.FileStorageService;
import com.joje.dbee.service.HttpRequestService;
import com.joje.dbee.vo.common.ResultVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/dbee/hipword/api")
public class HipwordRestController {

	@Autowired
	private HttpRequestService httpRequestService;
	
	@Autowired
	private FileStorageService fileStorageService;

	private static final String MELON_URL = "https://www.melon.com/chart/index.htm";
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private final String FILE_DIR = "\\hipword\\";
	
	private Gson gson = new Gson();
	
	@GetMapping(value = "/chart", produces = "application/json; charset=utf8")
	public ResponseEntity<?> getChart() throws Exception {
		
//		HTML 파일명 생성
		String date = LocalDate.now().format(formatter);
		Path filePath = fileStorageService.getPath(FILE_DIR, "melon_chart100_" + date + ".html");
		
		if (!fileStorageService.isFile(filePath)) {
//			HTML 요청
			Document doc = httpRequestService.requestHtml(MELON_URL);
//			HTML 파일 저장
			fileStorageService.write(doc.toString(), filePath);
		}
		
		fileStorageService.readToFile(filePath);
		
		ResultVo resultVo = new ResultVo();
//		resultVo.put("body", doc.body().toString());
		
		return new ResponseEntity<>(gson.toJson(resultVo), HttpStatus.OK);
	}
	
}
