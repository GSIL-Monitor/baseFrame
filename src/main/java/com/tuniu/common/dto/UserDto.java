package com.tuniu.common.dto;

import com.tuniu.common.dto.base.BaseDto;
import com.tuniu.common.model.User;

public class UserDto extends BaseDto<User> {
	
	private static final long serialVersionUID = 1L;

	private String usercode;

	private String username;
	
	private String password;
	
	private String salt;
	
	private Integer locked;
	
	private Integer roleId;//角色ID
	private Integer roleType;//角色类型

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	
}
