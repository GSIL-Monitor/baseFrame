package com.tuniu.common.service;

import com.tuniu.common.dto.RoleDto;
import com.tuniu.common.model.Role;
import com.tuniu.common.service.base.BaseService;

public interface RoleService extends BaseService<Role, RoleDto> {
	
	void deleteResources(Integer id);
	
	void addResources(Integer id, String[] resIds);
	
	Integer checkRelation(Integer roleId);
}
