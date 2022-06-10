package com.joje.dbee.dao.user;

import java.util.List;
import java.util.Map;

public interface UserDao {
	
	public List<Map<String, Object>> searchUserByKeyword(Map<String, Object> param) throws RuntimeException;

	public int countUserByKeyword(Map<String, Object> param) throws RuntimeException;

	public int countRoleByUserNo(Map<String, Object> param) throws RuntimeException;

	public Map<String, Object> selectOneUserByNo(long userNo) throws RuntimeException;

	public int insertUserRole(Map<String, Object> param) throws RuntimeException;

	public List<Map<String, Object>> selectRoleByUserNo(long userNo) throws RuntimeException;

	public int deleteUserRole(Map<String, Object> param) throws RuntimeException;
}
