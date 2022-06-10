package com.joje.dbee.service.admin;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joje.dbee.dao.user.UserDao;
import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.repository.account.RoleRepository;
import com.joje.dbee.repository.account.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "AdminService")
public class AdminServiceImpl implements AdminService {

	private static final String ROLE_ROOT = "ROLE_ROOT";

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public int countUserByKeyword(Map<String, Object> param) throws RuntimeException {
		return userDao.countUserByKeyword(param);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> searchUserByKeyword(Map<String, Object> param) throws RuntimeException {
		return userDao.searchUserByKeyword(param);
	}

	@Override
	public Map<String, Object> getUserInfo(long userNo) throws RuntimeException {
		return userDao.selectOneUserByNo(userNo);
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public int updateUserDetail(Map<String, Object> param) throws RuntimeException {
		int result = 0;
		long userNo = (long) param.get("userNo");

//		기존 유저 정보 조회
		UserEntity user = userRepository.findById(userNo).get();

//		이름 변경
		String newUserName = (String) param.get("userName");
		String oldUserName = user.getUserName();

//		기존 데이터와 다를 경우
		if (!oldUserName.equals(newUserName)) {
			user.setUserName(newUserName);
			userRepository.save(user);
		}

//		권한 정보 수정
		List<Map<String, Object>> hasRoles = userDao.selectRoleByUserNo(userNo);
		List<String> updateRoles = (List<String>) param.get("roles");

//		log.debug("[hasRoles]=[{}]", hasRoles);
//		log.debug("[updateRoles]=[{}]", updateRoles);

//		권한 부여
		for (String role : updateRoles) {
			boolean isDuplicate = false;
			int updateRoleId = Integer.parseInt(role);
			int i = 0;

//			업데이트된 권한 아이디와 기존 보유하고 있는 아이디 중복 검사
			for (i = 0; i < hasRoles.size(); i++) {
//				log.debug("[roleId]=[{}]", hasRoles.get(i).get("roleId"));
				int hasRoleId = (int) hasRoles.get(i).get("roleId");
				isDuplicate = hasRoleId == updateRoleId;
				if (isDuplicate)
					break;
			}

//			보유하고 있지 않다면 권한 부여
			if (!isDuplicate) {
				param.put("roleId", updateRoleId);
				result = userDao.insertUserRole(param);
				if (result != 0) {
					log.info("Grant ROLE : [ID]=[{}] [ROLE]=[{}]", user.getUserId(), updateRoleId);
				}
			}
		}

//		권한 제거
		for (int i = 0; i < hasRoles.size(); i++) {
			boolean isDuplicate = false;
			int hasRoleId = (int) hasRoles.get(i).get("roleId");

//			업데이트된 권한 아이디와 기존 보유하고 있는 아이디 중복 검사
			for (String role : updateRoles) {
//				루트 권한 제외
				if (ROLE_ROOT.equals(hasRoles.get(i).get("roleName"))) {
					isDuplicate = true;
					break;
				}

				int updateRoleId = Integer.parseInt(role);
				isDuplicate = hasRoleId == updateRoleId;
				if (isDuplicate) {
					break;
				}
			}

//			중복되지 않을 시 권한 제거
			if (!isDuplicate) {
				param.put("roleId", hasRoleId);
				result = userDao.deleteUserRole(param);
				if (result != 0) {
					log.info("REVOKE ROLE : [ID]=[{}] [ROLE]=[{}]", user.getUserId(), hasRoleId);
				}
			}
		}

		return result;
	}

}
