package com.tuniu.common.model;

import java.util.List;

import com.tuniu.common.model.base.BaseModel;

public class Role extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 角色名称 */
	private String name;
	
	/** 角色类型，1：基础员工（可查看自己名下数据），2：经理（可查看本组织内人员数据），3：管理员（可查看所有数据） */
	private Integer type;
	/** 是否可用,1：可用，0不可用 */
	private Integer available;
	
	/** 资源列表 */
	private List<Resource> resourceList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}
	

}
