package com.joje.dbee.service.admin;

import java.util.List;
import java.util.Map;

public interface AdminService {
	
	public int countUserByKeyword(Map<String, Object> param) throws RuntimeException;

	public List<Map<String, Object>> searchUserByKeyword(Map<String, Object> param) throws RuntimeException;

	public Map<String, Object> getUserInfo(long userNo) throws RuntimeException;

	public int updateUserDetail(Map<String, Object> param) throws RuntimeException;
	
}
