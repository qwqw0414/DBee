package com.joje.dbee.dao;

import java.util.List;
import java.util.Map;

public interface UserDao {
	
	List<Map<String, Object>> searchUserByKeyword(Map<String, Object> param) throws RuntimeException;

	int countUserByKeyword(Map<String, Object> param) throws RuntimeException;

	int countRoleByUserNo(Map<String, Object> param) throws RuntimeException;

	Map<String, Object> selectOneUserByNo(long userNo) throws RuntimeException;

	int insertUserRole(Map<String, Object> param) throws RuntimeException;

	List<Map<String, Object>> selectRoleByUserNo(long userNo) throws RuntimeException;

	int deleteUserRole(Map<String, Object> param) throws RuntimeException;
}
