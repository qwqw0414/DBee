package com.joje.dbee.entity.account;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.joje.dbee.common.contents.RoleType;

import lombok.Data;

@Data
@Entity(name = "TB_ROLE")
public class RoleEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	
	@Enumerated(EnumType.STRING)
	private RoleType roleName;
	
	@ManyToMany(mappedBy = "roles")
	private List<UserEntity> users;
	
}
