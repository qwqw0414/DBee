package com.joje.dbee.entity.account;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class UserRoleKey implements Serializable{
	
	private static final long serialVersionUID = -3343053815992237896L;
	
	@Column(name = "USER_NO")
	private Long userNo;
	
	@Column(name = "ROLE_ID")
	private Long roleId;
	
}
