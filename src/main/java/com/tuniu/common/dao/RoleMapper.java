package com.tuniu.common.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.common.dao.base.BaseMapper;
import com.tuniu.common.dto.RoleDto;
import com.tuniu.common.model.Role;

public interface RoleMapper extends BaseMapper<Role, RoleDto> {

	void deleteResources(Integer id);

	void addResources(List<Map<String, Object>> dataList);
	
	Integer checkRelation(Integer roleId);
}
