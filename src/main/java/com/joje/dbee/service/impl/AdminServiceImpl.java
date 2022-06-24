package com.joje.dbee.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joje.dbee.common.contents.RoleType;
import com.joje.dbee.dao.UserDao;
import com.joje.dbee.dto.account.RoleDto;
import com.joje.dbee.dto.account.UserDto;
import com.joje.dbee.entity.account.RoleEntity;
import com.joje.dbee.entity.account.UserEntity;
import com.joje.dbee.entity.account.UserRoleEntity;
import com.joje.dbee.entity.account.UserRoleKey;
import com.joje.dbee.repository.account.RoleRepository;
import com.joje.dbee.repository.account.UserRepository;
import com.joje.dbee.repository.account.UserRoleRepository;
import com.joje.dbee.service.AdminService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "AdminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;
	
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
	public UserDto getUserInfo(long userNo) throws RuntimeException {
		
		UserEntity user = userRepository.findByUserNo(userNo);
		
		UserDto result = new UserDto();
		modelMapper.map(user, result);
		
		return result;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public void updateUserDetail(UserDto userDto) throws RuntimeException {
		long userNo = userDto.getUserNo();

		UserEntity userEntity = userRepository.findByUserNo(userDto.getUserNo());
		userEntity.setUserId(userDto.getUserId());
		userEntity.setUserName(userDto.getUserName());
		
		userEntity = userRepository.save(userEntity);

//		권한 정보 수정
		List<RoleEntity> hasRoles = userEntity.getRoles();
		List<RoleDto> updateRoles = userDto.getRoles();

		log.debug("[hasRoles]=[{}]", hasRoles);
		log.debug("[updateRoles]=[{}]", updateRoles);

//		권한 부여
		for (RoleDto role : updateRoles) {
			boolean isDuplicate = false;
			long updateRoleId = role.getRoleId();
			int i = 0;

//			업데이트된 권한 아이디와 기존 보유하고 있는 아이디 중복 검사
			for (i = 0; i < hasRoles.size(); i++) {
//				log.debug("[roleId]=[{}]", hasRoles.get(i).get("roleId"));
				long hasRoleId = hasRoles.get(i).getRoleId();
				isDuplicate = hasRoleId == updateRoleId;
				if (isDuplicate)
					break;
			}

//			보유하고 있지 않다면 권한 부여
			if (!isDuplicate) {
				UserRoleKey key = new UserRoleKey();
				key.setRoleId(updateRoleId);
				key.setUserNo(userNo);
				
				UserRoleEntity userRoleEntity = new UserRoleEntity();
				userRoleEntity.setUserRoleId(key);
				
				userRoleRepository.save(userRoleEntity);
				log.info("Grant ROLE : [ID]=[{}] [ROLE]=[{}]", userEntity.getUserId(), updateRoleId);
			}
		}

//		권한 제거
		for (int i = 0; i < hasRoles.size(); i++) {
			boolean isDuplicate = false;
			long hasRoleId = hasRoles.get(i).getRoleId();

//			업데이트된 권한 아이디와 기존 보유하고 있는 아이디 중복 검사
			for (RoleDto role : updateRoles) {
//				루트 권한 제외
				if (RoleType.ROLE_ROOT.equals(hasRoles.get(i).getRoleName())) {
					isDuplicate = true;
					break;
				}

				long updateRoleId = role.getRoleId();
				isDuplicate = hasRoleId == updateRoleId;
				if (isDuplicate) {
					break;
				}
			}

//			중복되지 않을 시 권한 제거
			if (!isDuplicate) {
				UserRoleKey key = new UserRoleKey();
				key.setRoleId(hasRoleId);
				key.setUserNo(userNo);
				
				UserRoleEntity userRoleEntity = new UserRoleEntity();
				userRoleEntity.setUserRoleId(key);
				userRoleRepository.delete(userRoleEntity);
				log.info("REVOKE ROLE : [ID]=[{}] [ROLE]=[{}]", userEntity.getUserId(), hasRoleId);
			}
		}
	}

}
