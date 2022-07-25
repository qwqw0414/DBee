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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity(name = "TB_USER")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userNo;
	
	private String userId;
	private String userName;
	private String password;
	private LocalDateTime regDate;
	private boolean enabled;
	
	@JsonIgnore
	@ManyToMany()
	@JoinTable(
			name = "TB_USER_ROLE",
			joinColumns = @JoinColumn(name = "USER_NO"),
			inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<RoleEntity> roles = new ArrayList<>();
	
	@Override
	public String toString() {
		return "UserEntity [userNo=" + userNo + ", userId=" + userId + ", userName=" + userName + ", password=" + password
				+ ", regDate=" + regDate + ", enabled=" + enabled + "]";
	}
	
}
