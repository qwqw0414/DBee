package com.joje.dbee.entity.account;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "TB_USER")
@EqualsAndHashCode
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userNo;
	
	private String userId;
	private String userName;
	private String password;
	private LocalDateTime regDate = LocalDateTime.now();
	private boolean enabled;
	
	@ManyToMany
	@JoinTable(
			name = "TB_USER_ROLE",
			joinColumns = @JoinColumn(name = "USER_NO"),
			inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<RoleEntity> roles = new ArrayList<>();
	
	@Override
	public String toString() {
		return "UserVo [userNo=" + userNo + ", userId=" + userId + ", userName=" + userName + ", password=" + password
				+ ", regDate=" + regDate + ", enabled=" + enabled + "]";
	}
	
}
