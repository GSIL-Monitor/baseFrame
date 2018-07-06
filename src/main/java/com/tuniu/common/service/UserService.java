package com.tuniu.common.service;

import java.util.List;

import com.tuniu.common.dto.UserDto;
import com.tuniu.common.model.Resource;
import com.tuniu.common.model.Role;
import com.tuniu.common.model.User;
import com.tuniu.common.service.base.BaseService;

public interface UserService extends BaseService<User, UserDto> {
	
	void assignRole(User user);
	
	void revokeRole(User user);

	List<String> getRolesByUserId(Integer userId);
	
	List<Role> getUserRolesByUserId(Integer userId);//获取该用户所有的角色

	List<User> getUserDataCache();
	
	User getUserByName(String name);
	
	List<Resource> getResByUserName(String username);
	
	User getUserDetail(Integer id);
	
} 
