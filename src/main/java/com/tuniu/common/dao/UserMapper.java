package com.tuniu.common.dao;

import java.util.List;

import com.tuniu.common.dao.base.BaseMapper;
import com.tuniu.common.dto.UserDto;
import com.tuniu.common.model.Resource;
import com.tuniu.common.model.Role;
import com.tuniu.common.model.User;

public interface UserMapper extends BaseMapper<User, UserDto> {
	
	List<String> getRolesByUserId(Integer userId);//获取该用户所有的角色id
	
	List<Role> getUserRolesByUserId(Integer userId);//获取该用户所有的角色

	void assignRole(User user);
	
	void revokeRole(User user);

	int countUserRole(UserDto dto);

	List<User> listUserRole(UserDto dto);
	
	void addUserRoleRelation(UserDto dto);
	
	User getUserByName(String name);
	
	List<Resource> getResByUserName(String username);

}
