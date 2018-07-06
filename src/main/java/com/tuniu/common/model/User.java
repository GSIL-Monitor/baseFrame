package com.tuniu.common.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Size;

/**
 * 用户
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	/** 账号 */
	private String usercode;
	@Size(min=2,max=10,message="{user.name.length.error}")
	private String username;
	
	private String password;
	private String confirm_password;
	
	private String salt;
	
	private Integer locked;
	
	private List<Role> roles;
	
	private String roleIds;//一个用户可以有多个角色
	
	private Integer roleId;
	
	private List<User> pidList;
	
	private String pids;//一个用户可以有多个审批领导
	
	/** 资源列表 */
	private List<Resource> resourceList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getConfirm_password() {
		return confirm_password;
	}

	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}

	public List<User> getPidList() {
		return pidList;
	}

	public void setPidList(List<User> pidList) {
		this.pidList = pidList;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}
	
}
