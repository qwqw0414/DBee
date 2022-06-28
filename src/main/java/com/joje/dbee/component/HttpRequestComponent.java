package com.joje.dbee.component;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.joje.dbee.exception.HttpRequestException;

import lombok.extern.slf4j.Slf4j;

@Component(value = "HttpRequestComponent")
public class HttpRequestComponent {
	public Document requestHtml(String url) {
		try {
			return Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new HttpRequestException("html 연결 실패 url=" + url);
		}
	}
}
