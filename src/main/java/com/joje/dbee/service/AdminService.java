package com.joje.dbee.service;

import java.util.List;
import java.util.Map;

public interface AdminService {
	
	int countUserByKeyword(Map<String, Object> param) throws RuntimeException;

	List<Map<String, Object>> searchUserByKeyword(Map<String, Object> param) throws RuntimeException;

	Map<String, Object> getUserInfo(long userNo) throws RuntimeException;

	int updateUserDetail(Map<String, Object> param) throws RuntimeException;
	
}
