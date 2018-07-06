package com.tuniu.common.model;

import java.io.Serializable;
import java.util.List;

public class ActiveUser implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String usercode;
	private String username;
	private String password;
	private String salt;
	private char locked;
	
	private List<Resource> menus;
	private List<Resource> permissions;
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
	public char getLocked() {
		return locked;
	}
	public void setLocked(char locked) {
		this.locked = locked;
	}
	public List<Resource> getMenus() {
		return menus;
	}
	public void setMenus(List<Resource> menus) {
		this.menus = menus;
	}
	public List<Resource> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Resource> permissions) {
		this.permissions = permissions;
	}
	@Override
	public String toString() {
		return "ActiveUser [username=" + username + "]";
	}
	
	
}
