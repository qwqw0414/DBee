package com.joje.dbee.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joje.dbee.dao.UserDao;

@Repository(value = "UserDao")
public class UserDaoImpl implements UserDao{

	@Autowired
	private SqlSessionTemplate sst;
	
	@Override
	public List<Map<String, Object>> searchUserByKeyword(Map<String, Object> param) throws RuntimeException {
		return sst.selectList("user.searchUserByKeyword", param);
	}

	@Override
	public int countUserByKeyword(Map<String, Object> param) throws RuntimeException {
		return sst.selectOne("user.countUserByKeyword", param);
	}

	@Override
	public int countRoleByUserNo(Map<String, Object> param) throws RuntimeException {
		return sst.selectOne("user.countRoleByUserNo", param);
	}

	@Override
	public Map<String, Object> selectOneUserByNo(long userNo) throws RuntimeException {
		return sst.selectOne("user.selectOneUserByNo", userNo);
	}

	@Override
	public int insertUserRole(Map<String, Object> param) throws RuntimeException {
		return sst.insert("user.insertUserRole", param);
	}

	@Override
	public List<Map<String, Object>> selectRoleByUserNo(long userNo) throws RuntimeException {
		return sst.selectList("user.selectRoleByUserNo", userNo);
	}

	@Override
	public int deleteUserRole(Map<String, Object> param) throws RuntimeException {
		return sst.delete("user.deleteUserRole", param);
	}
	
}
