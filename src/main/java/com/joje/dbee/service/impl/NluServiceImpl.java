package com.joje.dbee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.joje.dbee.service.NluService;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "NluService")
public class NluServiceImpl implements NluService{
	
	@Override
	public void test() {
		Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);
		String str = "지금은 테스트하는 시간입니다.";
		
		KomoranResult analyzeResultList  = komoran.analyze(str);
		
		log.debug("[analyzeResultList ]=[{}]", analyzeResultList .getPlainText());

		List<Token> tokenList = analyzeResultList.getTokenList();
		for(Token token : tokenList) {
			log.debug("[token]=[{}]", token);
		}
		
	}
	
	@Override
	public List<String> analyzeAllByNN(List<String> list) {
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		List<String> result = new ArrayList<>();
		
		if(list != null) {
			for(String str : list) {
				KomoranResult analyzeResultList  = komoran.analyze(str);
				List<Token> tokenList = analyzeResultList.getTokenList();
				
				for(Token token : tokenList) {
					if(token.getPos().startsWith("NNG")) {
						result.add(token.getMorph());
					}
				}
			}
		}
		
		return result;
	}
	
	
	
	
	
	
}
