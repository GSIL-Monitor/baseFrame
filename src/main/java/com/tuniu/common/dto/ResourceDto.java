package com.tuniu.common.dto;

import com.tuniu.common.dto.base.BaseDto;
import com.tuniu.common.model.Resource;

public class ResourceDto extends BaseDto<Resource> {

	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private String url;
	private String percode;
	private Integer pid;
	private String pids;
	private String sortstring;
	private Integer available;
	private Integer roleId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPercode() {
		return percode;
	}
	public void setPercode(String percode) {
		this.percode = percode;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getPids() {
		return pids;
	}
	public void setPids(String pids) {
		this.pids = pids;
	}
	public String getSortstring() {
		return sortstring;
	}
	public void setSortstring(String sortstring) {
		this.sortstring = sortstring;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	

}
