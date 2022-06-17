package com.joje.dbee.service.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.joje.dbee.service.HttpRequestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "HttpRequestService")
public class HttpRequestServiceImpl implements HttpRequestService {

	@Override
	public Document requestHtml(String url) throws Exception {
		
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			log.error("html 연결 실패 [url]=[{}]", url);
		}
		
		return doc;
	}
}
