package com.tuniu.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.common.dao.UserMapper;
import com.tuniu.common.dto.UserDto;
import com.tuniu.common.model.Resource;
import com.tuniu.common.model.Role;
import com.tuniu.common.model.User;
import com.tuniu.common.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper mapper;
	
	@Override
	public void add(User user) {
		mapper.add(user);
		String roleIds = user.getRoleIds();
		String[] ids = roleIds.split(",");
		UserDto dto = new UserDto();
		for(String roleId : ids){
			dto.setId(user.getId());
			dto.setRoleId(Integer.parseInt(roleId));
			mapper.addUserRoleRelation(dto);
		}
		
	}

	@Override
	public void delete(Integer id) {		
		mapper.delete(id);
	}

	@Override
	public void update(User user) {
		mapper.update(user);		
		String roleIds = user.getRoleIds();
		if(roleIds!=null && !"".equals(roleIds)){
			//更新用户角色关联表,先删后插
			revokeRole(user);
			String[] roles = roleIds.split(",");
			for(String role : roles){
				user.setRoleId(Integer.parseInt(role.trim()));
				assignRole(user);
			}
		}
	}
	
	@Override
	public void revokeRole(User user) {
		mapper.revokeRole(user);
	};

	@Override
	public User get(Integer id) {
		//获取user
		User user = mapper.get(id);
		//获取user所有的角色
		List<String> roles = getRolesByUserId(id);
		user.setRoleIds(roles.toString().replace("[", "").replace("]", ""));
		return mapper.get(id);
	}

	@Override
	public List<User> list(UserDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void assignRole(User user) {
		mapper.assignRole(user);
	}	
	
	@Override
	public void loadPage(UserDto dto) {
		int totalRecords = mapper.countUserRole(dto);
		List<User> userList = mapper.listUserRole(dto);
		for(User user : userList){
			Integer userId = user.getId();
			List<Role> roles = getUserRolesByUserId(userId);
			user.setRoles(removeDuplicate(roles));
			//设置审批领导
			user.setPidList(getPidList(user.getPids()));
		}				
		dto.setTotalRecords(totalRecords);
		dto.setDataList(userList);
	}
	
	private List<User> getPidList(String pids) {
		List<User> list = new ArrayList<User>();
		String[] pidArr = pids.split(",");
		for(String pid : pidArr){
			if(pid!=null && !"".equals(pid))
				list.add(mapper.get(Integer.parseInt(pid)));
		}
		return list;
	}

	//去除重复的角色类型
	private List<Role> removeDuplicate(List<Role> list){
		for(int i=0;i<list.size()-1;i++){
			for(int j=list.size()-1;j>i;j--){
				if((list.get(j).getType()==list.get(i).getType())){
					list.get(i).setType(null);
				}
			}
		}
		return list;
	}

	@Override
	public List<User> getUserDataCache() {
		//缓存预留
		return mapper.list(new UserDto());
	}

	@Override
	public List<String> getRolesByUserId(Integer userId) {
		
		return mapper.getRolesByUserId(userId);
	}

	@Override
	public User getUserByName(String name) {
		return mapper.getUserByName(name);
	}

	@Override
	public List<Resource> getResByUserName(String username) {
		return mapper.getResByUserName(username);
	}

	@Override
	public List<Role> getUserRolesByUserId(Integer userId) {
		return mapper.getUserRolesByUserId(userId);
	}

	@Override
	public User getUserDetail(Integer id) {
		User user = mapper.get(id);
		List<Role> roles = getUserRolesByUserId(id);
		user.setRoles(removeDuplicate(roles));//去重后，设置角色集合
		List<Resource> permissions = mapper.getResByUserName(user.getUsercode());
		user.setResourceList(permissions);
		return user;
	}


}
