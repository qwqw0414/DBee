package com.joje.dbee.entity.account;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_USER_ROLE")
public class UserRoleEntity {

	@EmbeddedId
	private UserRoleKey userRoleId;

}
