package com.joje.dbee.service;

import org.jsoup.nodes.Document;

public interface HttpRequestService {

	public Document requestHtml(String url) throws Exception;
	
}
