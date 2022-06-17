package com.joje.dbee.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.joje.dbee.vo.hipword.SongVo;

public interface HipwordService {

	public List<SongVo> getCartList(Document doc);

}
