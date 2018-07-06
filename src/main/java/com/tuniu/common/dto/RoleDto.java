package com.tuniu.common.dto;

import java.util.List;

import com.tuniu.common.dto.base.BaseDto;
import com.tuniu.common.model.Role;

public class RoleDto extends BaseDto<Role> {
	
	private static final long serialVersionUID = 1L;

	private List<Integer> ids;
	
	private String name;
	/** 角色类型，1：基础员工（可查看自己名下数据），2：经理（可查看本组织内人员数据），3：管理员（可查看所有数据） */
	private Integer type;
	/** 是否可用,1：可用，0不可用 */
	private Integer available;

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

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

}
