package com.joje.dbee.common.contents;

public enum RoleType {
	ROLE_ROOT(0), ROLE_ADMIN(1), ROLE_USER(2);

	private Long roleId;

	RoleType(long roleId) {
		this.roleId = roleId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

}
